package com.example.functional.photos;

import com.example.interfaces.PhotoCotainable;
import com.example.interfaces.Scaleble;

/**
 * Created by Dmitrij on 03.01.2016.
 */
public interface SavePhotoFunction {
    Scaleble savePhoto(byte[] body, PhotoCotainable domain, String name);
}
