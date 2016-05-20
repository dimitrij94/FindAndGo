package com.example.neo_services.image;

import com.example.constants.image.ImageContainerType;
import com.example.interfaces.PhotoContainable;
import com.example.pojo.dto.PhotoDTO;
import javassist.tools.web.BadHttpRequest;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Dmitrij on 25.10.2015.
 */
public interface ImageService <T ,S extends PhotoContainable> {


    void savePhoto(PhotoDTO photoDTO, S placeUser) throws IOException, BadHttpRequest;

    BufferedImage getPhoto(ImageContainerType type, PhotoContainable user, String name, String contentType) throws IOException;

    T getPhoto(String photoName);
}
