package com.example.pojo.dto;

import javax.validation.constraints.Pattern;

/**
 * Created by Dmitrij on 17.10.2015.
 */
public class AddressDTO {

    private float longitude;
    private float latitude;
    private String fullAddress;

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
