package com.example.adapters.image;

import com.example.constants.image.ImageContainerType;
import com.example.dao.IDBBean;
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
    IDBBean dao;

    @Override
    public SavePhotoFunction getSaveFunction(ImageContainerType type) {

        if (type != null) {
            switch (type) {
                case PLACE_EMPLOYEE:
                    return dao::addEmployeePhoto;
                case PLACE_USER:
                    return dao::addPlaceUserPhoto;
                case PLACE_MENU:
                    return dao::addMenuPhoto;
                case PLACE:
                    return dao::addPlacePhoto;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public GetPhotoFunction getGetPhotoFunction(ImageContainerType type) {
        if (type != null) {
            switch (type) {
                case PLACE_EMPLOYEE:
                    return dao::getEmployeePhotoByName;
                case PLACE_USER:
                    return dao::getUserPhotoByName;
                case PLACE_MENU:
                    return dao::getMenuPhotoByName;
                case PLACE:
                    return dao::getPlacePhotoByName;
            }
        }

        throw new IllegalArgumentException();
    }

}
