package com.example.adapters.image;

import com.example.constants.image.ImageContainerType;
import com.example.functional.photos.GetPhotoFunction;
import com.example.functional.photos.SavePhotoFunction;

/**
 * Created by Dmitrij on 08.01.2016.
 */
public interface IImageTypeAdapter {

   SavePhotoFunction getSaveFunction(ImageContainerType type);

    GetPhotoFunction getGetPhotoFunction(ImageContainerType type);
}
