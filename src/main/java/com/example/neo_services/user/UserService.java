package com.example.neo_services.user;

import com.example.graph.PlaceUserOrder;
import com.example.graph.place.Place;
import com.example.graph.user.PlaceUser;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.UserDTO;

/**
 * Created by Dmitrij on 21.02.2016.
 */
public interface UserService  {
    PlaceUser placeUser();

    void addMembership(String name);

    void addMembership(Place place, PlaceUser user);

    void newUserComment(String comment, int rating, PlaceUserOrder order);

    PlaceUser newUser(UserDTO userDTO);

    PlaceUser placeUser(String name);

    void savePhoto(String userName, PhotoDTO photoDTO);

    boolean confirmToken(String name, String token);

    boolean checkCredetials(String userEmail, String userName);
}
