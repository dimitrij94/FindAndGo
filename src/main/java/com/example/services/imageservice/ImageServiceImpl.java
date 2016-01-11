package com.example.services.imageservice;

import com.example.adapters.image.IImageTypeAdapter;
import com.example.constants.image.ImageContainerType;
import com.example.constants.image.sizes.ImageSize;
import com.example.dao.IDBBean;
import com.example.functional.photos.GetPhotoFunction;
import com.example.functional.photos.SavePhotoFunction;
import com.example.interfaces.PhotoCotainable;
import com.example.pojo.dto.PhotoDTO;
import com.example.services.MyExecutorService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    IDBBean dao;

    @Autowired
    MyExecutorService threadPoolContainer;

    @Autowired
    IImageTypeAdapter adapter;

    public void upload(PhotoDTO image, PhotoCotainable domain, ImageContainerType type) throws BadHttpRequest {

        ExecutorService threadPool = threadPoolContainer.getExecutor();

        SavePhotoFunction saveFunction = adapter.getSaveFunction(type);
        GetPhotoFunction getPhotoFunction = adapter.getGetPhotoFunction(type);

        byte[] imageBody = image.getImage().getBytes();

        saveFunction.savePhoto(imageBody, domain, "main");
/*
        final BufferedImage finalBufferedImage =
                cropImage(
                        getReadableImage(
                                getPhotoFunction.getByName("main", domain.getId())
                                        .getBody()), image);
*/
        final BufferedImage finalBufferedImage =
                getReadableImage(getPhotoFunction.getByName("main", domain.getId()).getBody());

        if(finalBufferedImage !=null) {
            for (ImageSize size : type.getSizes()) {
                threadPool.execute(() -> {
                    BufferedImage bufferedImage = null;
                    try {
                        int imageWidth = finalBufferedImage.getWidth();
                        bufferedImage = imageWidth <= size.getWidth() ?
                                finalBufferedImage :
                                scaleImage(finalBufferedImage, size);
                        saveFunction
                                .savePhoto(convertImage(bufferedImage), domain, size.getName());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        else throw new BadHttpRequest();
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

/*
    private void saveMenuImage(byte[] image, PlaceMenu menu, String name) {
        PlaceMenuPhoto photo = new PlaceMenuPhoto(image);
        photo.setName(name);
        dao.addMenuPhoto(menu, photo,image, );
    }

    private void savePlaceImage(byte[] image, Place place, String name) {
        PlacePhoto photo = new PlacePhoto(image);
        dao.addPlacePhoto(photo, place, name);
    }


    private void saveEmployeeImage(byte[] imageBody, PlaceEmployee employee, String name) {
        eDao.addEmployeePhoto(new PlaceEmployeePhoto(imageBody, employee, name));
    }
}


    public void uploadMenuPhoto(PhotoDTO image,
                                PlaceMenu menu) {


        byte[] imageBody = image.getImage().getBytes();
        saveMenuImage(imageBody, menu, "main");
        for (MenuImageSizes size : MenuImageSizes.values()) {
            new Thread(() -> {
                BufferedImage croppedImage = null;
                try {
                    croppedImage = cropImage(getReadableImage(dao.getMenuImageBodyByName(menu.getId(), "main")), image);
                    croppedImage = croppedImage.getWidth() <= size.getWidth() ||
                            croppedImage.getHeight() <= size.getHeight() ? croppedImage :
                            scaleImage(croppedImage, size);
                    saveMenuImage(convertImage(croppedImage), menu, size.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
    public void uploadPlacePhoto(PhotoDTO image, Place place) {
        byte[] imageBody = image.getImage().getBytes();
        savePlaceImage(imageBody, place, "main");
        for (PlaceImageSizes size : PlaceImageSizes.values()) {
            new Thread(() -> {
                dao.getPlaceImageBodyByName(place.getId(), "main");
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = cropImage(getReadableImage(imageBody), image);
                    int imageWidth = bufferedImage.getWidth();
                    bufferedImage = imageWidth <= size.getWidth() ? bufferedImage :
                            scaleImage(bufferedImage, size);
                    savePlaceImage(convertImage(bufferedImage), place, size.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    public void uploadEmployeePhoto(PhotoDTO photo, PlaceEmployee employee) {
        byte[] imageBody = photo.getImage().getBytes();
        saveEmployeeImage(imageBody, employee, "main");
        for (EmployeeImageSizes size : EmployeeImageSizes.values()) {
            new Thread(() -> {
                BufferedImage croppedImage = null;
                try {
                    croppedImage = cropImage(getReadableImage(eDao.getEmployeeMainPhoto(employee.getId(), "main")), photo);
                    croppedImage = croppedImage.getWidth() <= size.getWidth() ||
                            croppedImage.getHeight() <= size.getHeight() ? croppedImage :
                            scaleImage(croppedImage, size);
                    saveEmployeeImage(convertImage(croppedImage), employee, size.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

*/