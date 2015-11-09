package com.example.pojo.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Dmitrij on 02.11.2015.
 */
public class PlaceDTO {
    private String name;
    private String description;
    private String specialization;
    private AddressDTO address;
    private PhotoDTO photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }
}