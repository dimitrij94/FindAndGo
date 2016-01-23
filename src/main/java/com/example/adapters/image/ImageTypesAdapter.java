package com.example.adapters.image;

import com.example.constants.image.ImageContainerType;
import com.example.dao.photos.PhotoDAO;
import com.example.functional.photos.GetPhotoFunction;
import com.example.functional.photos.SavePhotoFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitrij on 08.01.2016.
 */
@Component
public class ImageTypesAdapter implements IImageTypeAdapter {

    @Autowired
    PhotoDAO photoDAO;

    @Override
    public SavePhotoFunction getSaveFunction(ImageContainerType type) {

        if (type != null) {
            switch (type) {
                case PLACE_EMPLOYEE:
                    return photoDAO::addEmployeePhoto;
                case PLACE_USER:
                    return photoDAO::addPlaceUserPhoto;
                case PLACE_MENU:
                    return photoDAO::addMenuPhoto;
                case PLACE:
                    return photoDAO::addPlacePhoto;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public GetPhotoFunction getGetPhotoFunction(ImageContainerType type) {
        if (type != null) {
            switch (type) {
                case PLACE_EMPLOYEE:
                    return photoDAO::getEmployeePhotoByName;
                case PLACE_USER:
                    return photoDAO::getUserPhotoByName;
                case PLACE_MENU:
                    return photoDAO::getMenuPhotoByName;
                case PLACE:
                    return photoDAO::getPlacePhotoByName;
            }
        }

        throw new IllegalArgumentException();
    }

}
