package com.example.services.userservice;

import com.example.domain.Place;
import com.example.domain.PlaceUser;
import com.example.pojo.dto.UserPlaceOrdersDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Dmitrij on 21.10.2015.
 */
public interface UserService {

    PlaceUser placeUser();

    Place findPlaceByOwnerId(PlaceUser user, long id);

    List<UserPlaceOrdersDTO> getOrderedServices(PlaceUser user);

    boolean isAuthenticated();


    int newUserLikes(long id);

    void newUserComment(String comment, int rating, long id);

    boolean isUser(PlaceUser user);
}
