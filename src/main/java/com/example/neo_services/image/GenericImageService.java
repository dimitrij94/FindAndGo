package com.example.neo_services.image;

import com.example.constants.image.ImageContainerType;
import com.example.constants.image.sizes.ImageSize;
import com.example.interfaces.PhotoContainable;
import com.example.neo_services.MyExecutorService;
import com.example.pojo.dto.PhotoDTO;
import javassist.tools.web.BadHttpRequest;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by Dmitrij on 19.05.2016.
 */
@Service
public abstract class GenericImageService<T, S extends PhotoContainable> implements ImageService<T, S>{

    private static String IMAGE_ROOT_LOCATION = "Z:\\home\\localhost\\KeyStyle\\images\\";

    @Autowired
    public MyExecutorService threadPoolContainer;

    @Autowired
    public GraphDatabase db;

    private ExecutorService threadPool;

    @PostConstruct
    public void getExecutorService() {
        threadPool = threadPoolContainer.getExecutor();
    }


    protected abstract CrudRepository<T, String> getRepository();

    protected abstract T getPhoto(int width, int height, ImageSize size, S domain);

    protected abstract ImageSize[] getPhotoSizes();


    public void savePhoto(PhotoDTO photoDTO, S photoContainable) throws IOException, BadHttpRequest {
        CrudRepository<T, String> repository = getRepository();

        CommonsMultipartFile imageFile = photoDTO.getImage();
        BufferedImage image = ImageIO.read(imageFile.getInputStream());
        ImageSize[] sizes = getPhotoSizes();
        try (Transaction tx = db.beginTx()) {
            T photo = getPhoto(image.getWidth(), image.getHeight(), sizes[0], photoContainable);
            repository.save(photo);

            for (int i = 1; i < sizes.length; i++) {
                ImageSize size = sizes[i];
                photo = getPhoto(size.getWidth(), size.getHeight(), size, photoContainable);
                getRepository().save(photo);
            }
            tx.success();
        }
        upload(image, photoContainable, imageFile.getContentType());

    }

    public BufferedImage getPhoto(ImageContainerType type, PhotoContainable user, String name, String contentType) throws IOException {
        File file = new File(getImageLocation(contentType, name));
        return ImageIO.read(file);
    }

    private void upload(BufferedImage image,
                        PhotoContainable domain,
                        String contentType) throws BadHttpRequest, IOException {
        ImageContainerType type = getImageContainerType();
        String mainPath = getImageLocation(contentType, ImageSize.MAIN_PHOTO_NAME);
        savePhoto(image, mainPath, contentType);

        final BufferedImage finalBufferedImage = getPhoto(type, domain, ImageSize.MAIN_PHOTO_NAME, contentType);

        for (ImageSize size : type.getSizes()) {
            threadPool.execute(() -> {
                BufferedImage bufferedImage = null;
                try {
                    int imageWidth = finalBufferedImage.getWidth();
                    bufferedImage = imageWidth <= size.getWidth() ?
                            finalBufferedImage :
                            scaleImage(finalBufferedImage, size);
                    String path = getImageLocation(contentType, size.getName());
                    savePhoto(bufferedImage, path, contentType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private String getImageLocation(String contentType, String name) {
        String imageType = getFileExtention(contentType);
        return IMAGE_ROOT_LOCATION + getImageContainerType().getLocation() + "//" + getImageContainerType() + "//" + name + "." + imageType;
    }

    protected abstract ImageContainerType getImageContainerType();

    protected String getFileExtention(String contentType) {
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
