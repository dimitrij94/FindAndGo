package com.example.constants.image.sizes;

/**
 * Created by Dmitrij on 03.01.2016.
 */
public enum UserImageSizes implements ImageSize {
    USER_PROFILE_IMAGE(230, 230, "profile"),
    USER_TOOLBAR_IMAGE(80,80,"toolbar");

    int width;
    int height;
    int index;
    String name;

    UserImageSizes(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.index = width/height;
        this.name = name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
