package com.example.constants.image;

import com.example.constants.image.sizes.*;

/**
 * Created by Dmitrij on 08.01.2016.
 */
public enum ImageContainerType {

    PLACE_USER(UserImageSizes.values()),
    PLACE(PlaceImageSizes.values()),
    PLACE_EMPLOYEE(EmployeeImageSizes.values()),
    PLACE_MENU(MenuImageSizes.values());

    ImageSize[] sizes;

    ImageContainerType(ImageSize[] sizes) {
        this.sizes = sizes;
    }

    public ImageSize[] getSizes() {
        return sizes;
    }
}
