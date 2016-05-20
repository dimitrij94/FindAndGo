package com.example.constants.image;

import com.example.constants.image.sizes.*;

/**
 * Created by Dmitrij on 08.01.2016.
 */
public enum ImageContainerType {

    PLACE_USER(UserImageSizes.values(),"//users"),
    PLACE(PlaceImageSizes.values(),"//places"),
    PLACE_EMPLOYEE(EmployeeImageSizes.values(),"//employees"),
    PLACE_MENU(MenuImageSizes.values(),"//menus");

    private ImageSize[] sizes;
    private String location;

    ImageContainerType(ImageSize[] sizes,String location) {
        this.location = location;
        this.sizes = sizes;
    }

    public String getLocation() {
        return location;
    }

    public ImageSize[] getSizes() {
        return sizes;
    }
}
