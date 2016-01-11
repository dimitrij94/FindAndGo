package com.example.functional.photos;

import com.example.interfaces.Scaleble;

/**
 * Created by Dmitrij on 03.01.2016.
 */
public interface GetPhotoFunction {

    Scaleble getByName(String name, long id);
}
