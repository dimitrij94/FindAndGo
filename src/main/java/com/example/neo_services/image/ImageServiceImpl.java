package com.example.neo_services.image;

import com.example.constants.image.ImageContainerType;
import com.example.constants.image.sizes.ImageSize;
import com.example.constants.image.sizes.MenuImageSizes;
import com.example.constants.image.sizes.PlaceImageSizes;
import com.example.constants.image.sizes.UserImageSizes;
import com.example.graph.photos.PlaceMenuServicePhoto;
import com.example.graph.photos.PlacePhoto;
import com.example.graph.photos.PlaceUserPhoto;
import com.example.graph.place.Place;
import com.example.graph.service.PlaceMenuService;
import com.example.graph.user.PlaceUser;
import com.example.graph_repositories.menu.PlaceServicePhotoRepository;
import com.example.graph_repositories.place.PlacePhotoRepository;
import com.example.graph_repositories.user.PlaceUserPhotoRepository;
import com.example.interfaces.PhotoCotainable;
import com.example.neo_services.MyExecutorService;
import com.example.pojo.dto.PhotoDTO;
import javassist.tools.web.BadHttpRequest;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    public ImageServiceImpl(PlacePhotoRepository placePhotoRepository,
                            PlaceServicePhotoRepository placeServicePhotoRepository,
                            GraphDatabase db,
                            MyExecutorService threadPoolContainer,
                            PlaceUserPhotoRepository placeUserPhotoRepository) {
        this.placePhotoRepository = placePhotoRepository;
        this.placeServicePhotoRepository = placeServicePhotoRepository;
        this.db = db;
        this.threadPoolContainer = threadPoolContainer;
        this.placeUserPhotoRepository = placeUserPhotoRepository;
    }


    private MyExecutorService threadPoolContainer;
    private PlacePhotoRepository placePhotoRepository;
    private PlaceServicePhotoRepository placeServicePhotoRepository;
    private PlaceUserPhotoRepository placeUserPhotoRepository;
    private GraphDatabase db;


    @Override
    public void savePlaceUserPhoto(PhotoDTO photoDTO, PlaceUser placeUser) throws IOException, BadHttpRequest {
        CommonsMultipartFile imageFile = photoDTO.getImage();
        BufferedImage image = ImageIO.read(imageFile.getInputStream());

        List<Long> ids = new ArrayList<>();
        try (Transaction tx = db.beginTx()) {
            PlaceUserPhoto photo = new PlaceUserPhoto(image.getWidth(), image.getHeight());
            placeUserPhotoRepository.save(photo);
            ids.add(photo.getId());

            for (ImageSize size : UserImageSizes.values()) {
                photo = new PlaceUserPhoto(size.getWidth(), size.getHeight());
                placeUserPhotoRepository.save(photo);
                ids.add(photo.getId());
            }
            tx.success();
        }
        upload(image, placeUser, ImageContainerType.PLACE_USER, imageFile.getContentType(), ids);

    }

    @Override
    public void savePlaceMenuServicePhoto(PhotoDTO photoDTO, PlaceMenuService service) throws IOException, BadHttpRequest {
        CommonsMultipartFile imageFile = photoDTO.getImage();
        BufferedImage image = ImageIO.read(imageFile.getInputStream());
        List<Long> ids = new ArrayList<>();

        try (Transaction tx = db.beginTx()) {
            PlaceMenuServicePhoto photo = new PlaceMenuServicePhoto(image.getWidth(), image.getHeight());
            placeServicePhotoRepository.save(photo);
            ids.add(photo.getId());

            for (ImageSize size : MenuImageSizes.values()) {
                photo = new PlaceMenuServicePhoto(size.getWidth(), size.getHeight());

                placeServicePhotoRepository.save(photo);
                ids.add(photo.getId());
            }
            tx.success();
        }
        upload(image, service, ImageContainerType.PLACE_MENU, imageFile.getContentType(), ids);
    }


    @Override
    public void savePlacePhoto(PhotoDTO photoDTO, Place place) throws IOException, BadHttpRequest {
        CommonsMultipartFile photoFile = photoDTO.getImage();
        BufferedImage image = ImageIO.read(photoFile.getInputStream());
        PlacePhoto photo = new PlacePhoto();
        List<Long> ids = new ArrayList<>(PlaceImageSizes.values().length);

        try (Transaction tx = db.beginTx()) {

            for (ImageSize size : PlaceImageSizes.values()) {
                photo.setPlace(place);
                photo.setWidth(size.getWidth());
                photo.setHeigth(size.getHeight());
                photo = placePhotoRepository.save(photo);
                ids.add(photo.getId());
            }
            tx.success();
        }
        upload(image, place, ImageContainerType.PLACE, photoFile.getContentType(), ids);
    }

    private void upload(BufferedImage image,
                        PhotoCotainable domain,
                        ImageContainerType type,
                        String contentType,
                        List<Long> photoId) throws BadHttpRequest, IOException {

        ExecutorService threadPool = threadPoolContainer.getExecutor();
        String mainPath = getImageLocation(contentType, type, domain, photoId.get(0));
        savePhoto(image, mainPath, contentType);


        final BufferedImage finalBufferedImage = getPhoto(type, domain, photoId.get(0));

        for (int i = 1; i < photoId.size(); i++) {
            ImageSize size = type.getSizes()[i];
            final int finalI = i;
            threadPool.execute(() -> {
                BufferedImage bufferedImage = null;
                try {
                    int imageWidth = finalBufferedImage.getWidth();
                    bufferedImage = imageWidth <= size.getWidth() ?
                            finalBufferedImage :
                            scaleImage(finalBufferedImage, size);
                    String path = getImageLocation(contentType, type, domain, photoId.get(finalI));
                    savePhoto(bufferedImage, path, contentType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    @Override
    public BufferedImage getPhoto(ImageContainerType type, PhotoCotainable user, long id) throws IOException {
        File file = new File(String.join("webapp/", type.getLocation(), "/", user.getUserName(), "/"));
        return ImageIO.read(file);
    }

    private void savePhoto(InputStream imageInputStram, String path) {
        File file = new File(path);
        try (BufferedInputStream in = new BufferedInputStream(imageInputStram);
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {

            int bufferSize = 8192;
            byte[] buffer = new byte[bufferSize];
            int offset = 0;
            int bytesRead;

            while ((bytesRead = in.read(buffer, offset, bufferSize)) != -1) {
                out.write(buffer, offset, bytesRead);
                offset += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getImageLocation(String contentType, ImageContainerType type, PhotoCotainable user, long id) {
        String imageType = getFileExtention(contentType);
        return "webapp/static/images/" + type.getLocation() + "/" + user.getUserName() + "/" + id + "." + imageType;
    }

    private String getFileExtention(String contentType) {
        return contentType.split("/")[1];
    }

    private void savePhoto(BufferedImage image, String path, String contentType) throws IOException {
        ImageIO.write(image, getFileExtention(contentType), new File(path));
    }


    private BufferedImage getReadableImage(byte[] imageBody) {
        InputStream inputStream = new ByteArrayInputStream(imageBody);
        try {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage cropImage(BufferedImage bufferedImage, PhotoDTO image) {
        int x = Math.round(image.getX());
        int y = Math.round(image.getY());
        int w = Math.round(image.getW());
        int h = Math.round(image.getH());
        return bufferedImage.getSubimage(x, y, w, h);
    }

    private BufferedImage scaleImage(BufferedImage originImage, ImageSize size) {
        int type = originImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originImage.getType();
        BufferedImage changedImage = new BufferedImage(size.getWidth(), size.getHeight(), type);
        Graphics2D g = changedImage.createGraphics();
        g.drawImage(originImage, 0, 0, size.getWidth(), size.getHeight(), null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return changedImage;
    }

    private byte[] convertImage(BufferedImage image) throws IOException {
        try (ByteArrayOutputStream baoStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpg", baoStream);
            baoStream.flush();
            return baoStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IOException("failed to read bytes from image");
    }


}
