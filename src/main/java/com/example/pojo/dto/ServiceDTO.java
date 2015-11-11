package com.example.pojo.dto;

/**
 * Created by Dmitrij on 04.11.2015.
 */
public class ServiceDTO {
    String serviceName;
    String serviceDescription;
    int servicePrice;
    long menuId;
    int serviceHours;
    int serviceMinutes;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public int getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(int serviceHours) {
        this.serviceHours = serviceHours;
    }

    public int getServiceMinutes() {
        return serviceMinutes;
    }

    public void setServiceMinutes(int serviceMinutes) {
        this.serviceMinutes = serviceMinutes;
    }
}
