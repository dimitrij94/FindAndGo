package com.example.pojo.dto;

import java.util.Collection;

/**
 * Created by Dmitrij on 24.02.2016.
 */
public class PlaceOrderDTO {
    private String placeName;
    private String placeEmployeeName;
    private String placeServiceName;
    private Collection<String> placeAdditionalServicesNamesList;



    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceEmployeeName() {
        return placeEmployeeName;
    }

    public void setPlaceEmployeeName(String placeEmployeeName) {
        this.placeEmployeeName = placeEmployeeName;
    }

    public String getPlaceServiceName() {
        return placeServiceName;
    }

    public void setPlaceServiceName(String placeServiceName) {
        this.placeServiceName = placeServiceName;
    }

    public Collection<String> getPlaceAdditionalServicesNamesList() {
        return placeAdditionalServicesNamesList;
    }

    public void setPlaceAdditionalServicesNamesList(Collection<String> placeAdditionalServicesNamesList) {
        this.placeAdditionalServicesNamesList = placeAdditionalServicesNamesList;
    }
}
