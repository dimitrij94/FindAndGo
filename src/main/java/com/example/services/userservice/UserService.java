package com.example.services.userservice;

import com.example.domain.Place;
import com.example.domain.PlaceUser;
import com.example.pojo.dto.UserPlaceOrdersDTO;

import java.util.List;

/**
 * Created by Dmitrij on 21.10.2015.
 */
public interface UserService {

    PlaceUser placeUser();

    Place findPlaceByOwnerId(PlaceUser user, long id);

    List<UserPlaceOrdersDTO> getOrderedServices(PlaceUser user);

    boolean isAuthenticated();


}
