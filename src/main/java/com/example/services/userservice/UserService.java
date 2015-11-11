package com.example.services.userservice;

import com.example.domain.Order;
import com.example.domain.Place;
import com.example.domain.PlaceUser;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by Dmitrij on 21.10.2015.
 */
public interface UserService {

    PlaceUser placeUser();

    Place findPlaceByOwnerId(PlaceUser user, long id);

    Map<Place,List<Order>> getOrderedServices(PlaceUser user);
}
