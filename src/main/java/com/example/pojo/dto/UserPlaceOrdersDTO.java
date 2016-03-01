package com.example.pojo.dto;

import com.example.graph.PlaceUserOrder;
import com.example.graph.place.Place;

import java.util.List;

/**
 * Created by Dmitrij on 13.11.2015.
 */
public class UserPlaceOrdersDTO {
    Place place;
    List<PlaceUserOrder> userUserOrderses;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<PlaceUserOrder> getUserUserOrderses() {
        return userUserOrderses;
    }

    public void setUserUserOrderses(List<PlaceUserOrder> userUserOrderses) {
        this.userUserOrderses = userUserOrderses;
    }
}
