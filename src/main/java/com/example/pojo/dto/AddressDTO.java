package com.example.pojo.dto;

import com.example.domain.users.UserAddress;

/**
 * Created by Dmitrij on 17.10.2015.
 */
public class AddressDTO {

    private float longitude;
    private float latitude;

    private String country;
    private String city;
    private String town;
    private String district;

    public AddressDTO(UserAddress address) {
        this.latitude=Float.valueOf(address.getLatitude());
        this.longitude=Float.valueOf(address.getLongitude());
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
