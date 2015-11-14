package com.example.services.imageservice;

import com.example.domain.Place;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.domain.photos.PlacePhoto;
import com.example.dao.IDBBean;
import com.example.pojo.dto.PhotoDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.*;

/**
 * Created by Dmitrij on 25.10.2015.
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    IDBBean dao;

    private Log log = LogFactory.getLog(this.getClass());

    public enum ImageSize {
        PLACE_PROFILE_IMAGE_SIZE(480, 271),
        PLACE_PROFILE_MENU_IMAGE_SIZE(267, 150);

        int height;
        int width;
        int index;
        public int getIndex(){
            return index;
        }
        ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
            this.index=Math.round((width/height)*10);
        }
    }

    @Override
    public void uploadPlaceMainPhoto(PhotoDTO image, Place place) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = cropImage(getReadableImage(image.getImage()), image);
            bufferedImage = bufferedImage.getWidth() <= ImageSize.PLACE_PROFILE_IMAGE_SIZE.width ? bufferedImage :
                    scaleImage(bufferedImage, ImageSize.PLACE_PROFILE_IMAGE_SIZE);
            savePlaceImage(convertImage(bufferedImage), place, "small");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadMenuPhoto(PhotoDTO image,
                                PlaceMenu menu) {

        BufferedImage croppedImage = null;
        try {
            croppedImage = cropImage(getReadableImage(image.getImage()), image);
            croppedImage = croppedImage.getWidth() <= ImageSize.PLACE_PROFILE_MENU_IMAGE_SIZE.width ||
                    croppedImage.getHeight() <= ImageSize.PLACE_PROFILE_MENU_IMAGE_SIZE.height ? croppedImage :
                    scaleImage(croppedImage, ImageSize.PLACE_PROFILE_MENU_IMAGE_SIZE);

            saveMenuImage(convertImage(croppedImage), menu, "small");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private BufferedImage getReadableImage(MultipartFile image) throws IOException {
        byte[] imageData = image.getBytes();
        InputStream inputStream = new ByteArrayInputStream(imageData);
        return ImageIO.read(inputStream);
    }

    private BufferedImage cropImage(BufferedImage bufferedImage, PhotoDTO image)
            throws ImagingOpException {
        int x = Math.round(image.getX());
        int y = Math.round(image.getY());
        int w = Math.round(image.getW());
        int h = Math.round(image.getH());

        return bufferedImage.getSubimage(x, y, w, h);

    }

    private BufferedImage scaleImage(BufferedImage originImage, ImageSize size) {
        int type = originImage.getType()==0?BufferedImage.TYPE_INT_ARGB:originImage.getType();
        BufferedImage changedImage = new BufferedImage(size.width, size.height, type);
        Graphics2D g = changedImage.createGraphics();
        g.drawImage(originImage, 0, 0,size.width,size.height, null);
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

    private void saveMenuImage(byte[] image, PlaceMenu menu, String name) throws IOException {
        PlaceMenuPhoto photo = new PlaceMenuPhoto(image);
        photo.setName(name);
        dao.saveMenuPhoto(menu, photo);
    }

    private void savePlaceImage(byte[] image, Place place, String name) throws IOException {
        PlacePhoto photo = new PlacePhoto(image);
        photo.setName(name);
        dao.addPlacePhoto(photo, place);
    }

    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
