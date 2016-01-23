package com.example.pojo.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Dmitrij on 03.11.2015.
 */
public class PhotoDTO {
    float x;
    float y;
    float w;
    float h;

    private CommonsMultipartFile image;

    public PhotoDTO(CommonsMultipartFile image) {
        this.image = image;
    }

    public PhotoDTO() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public CommonsMultipartFile getImage() {
        return image;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }
}
