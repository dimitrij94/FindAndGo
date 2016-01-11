package com.example.constants.image.sizes;

/**
 * Created by Dmitrij on 06.12.2015.
 */
public enum MenuImageSizes implements ImageSize{
    INDEX_TWO_(800, 450, "800"),
    INDEX_THREE(600,337, "600"),
    PLACE_PROFILE_MENU(300, 169, "300");

    int height;
    int width;
    int index;
    String name;

    MenuImageSizes(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
        this.index = Math.round((width / height)*10);
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
