package com.example.constants.image.sizes;

/**
 * Created by Dmitrij on 06.12.2015.
 */
public enum PlaceImageSizes implements ImageSize {

    INDEX_TWO_IMAGE_SIZE(800, 450, "800"),
    PLACE_PROFILE_IMAGE_SIZE(480, 271, "480");

    private int height;
    private int width;
    private int index;
    private String name;

    PlaceImageSizes(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
        this.index = Math.round(width / height);
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
