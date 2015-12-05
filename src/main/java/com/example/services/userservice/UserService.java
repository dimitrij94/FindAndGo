package com.example.services.userservice;

import com.example.domain.Place;
import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.UserPlaceOrdersDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Created by Dmitrij on 21.10.2015.
 */
public interface UserService {

    PlaceUser placeUser();

    boolean hasRole(Authentication authentication, String role);

    List<UserPlaceOrdersDTO> getOrderedServices(PlaceUser user);

    boolean isAuthenticated();


    int newUserLikes(long id);

    void newUserComment(String comment, int rating, long id);

    PlaceOwner placeOwner();

}
