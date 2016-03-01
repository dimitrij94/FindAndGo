package com.example.neo_services.image;

import com.example.constants.image.ImageContainerType;
import com.example.graph.place.Place;
import com.example.graph.service.PlaceMenuService;
import com.example.graph.user.PlaceUser;
import com.example.interfaces.PhotoCotainable;
import com.example.pojo.dto.PhotoDTO;
import javassist.tools.web.BadHttpRequest;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Dmitrij on 25.10.2015.
 */
public interface ImageService {


    void savePlaceUserPhoto(PhotoDTO photoDTO, PlaceUser placeUser) throws IOException, BadHttpRequest;

    void savePlaceMenuServicePhoto(PhotoDTO photoDTO, PlaceMenuService service) throws IOException, BadHttpRequest;

    void savePlacePhoto(PhotoDTO photoDTO, Place place) throws IOException, BadHttpRequest;

    BufferedImage getPhoto(ImageContainerType type, PhotoCotainable user, long id) throws IOException;
}
