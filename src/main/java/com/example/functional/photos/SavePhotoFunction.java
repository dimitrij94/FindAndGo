package com.example.functional.photos;

import com.example.interfaces.PhotoCotainable;

/**
 * Created by Dmitrij on 03.01.2016.
 */
public interface SavePhotoFunction {
    void savePhoto(byte[] body, PhotoCotainable domain, String name);
}
