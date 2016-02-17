package com.example.services.userservice;

import com.example.domain.owner.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.domain.users.PlaceUserPhoto;
import com.example.pojo.dto.UserDTO;
import com.example.pojo.dto.UserPlaceOrdersDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Dmitrij on 21.10.2015.
 */
public interface UserService {

    PlaceUser placeUser();

    boolean hasRole(Authentication authentication, String role);

    List<UserPlaceOrdersDTO> getOrderedServices(PlaceUser user);

    boolean isAuthenticated();

    PlaceUser register(UserDTO blank, HttpServletRequest request);

    int newUserLikes(long id);

    void newUserComment(String comment, int rating, long id);

    PlaceOwner placeOwner();

    boolean validate(UserDTO user);

    ResponseEntity<Void> newUser(UserDTO user, HttpServletRequest request, UriComponentsBuilder ucBuilder);

    PlaceUserPhoto getUserPhotoByName(String name, long id);

    @PreAuthorize("#id==principal.id")
    PlaceUser getUser(long id);
}
