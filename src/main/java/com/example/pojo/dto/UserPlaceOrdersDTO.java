package com.example.pojo.dto;

import com.example.domain.UserOrders;
import com.example.domain.Place;

import java.util.List;

/**
 * Created by Dmitrij on 13.11.2015.
 */
public class UserPlaceOrdersDTO {
    Place place;
    List<UserOrders> userUserOrderses;



    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<UserOrders> getUserUserOrderses() {
        return userUserOrderses;
    }

    public void setUserUserOrderses(List<UserOrders> userUserOrderses) {
        this.userUserOrderses = userUserOrderses;
    }
}
