package com.example.services.userservice;

import com.example.dao.IDBBean;
import com.example.domain.Place;
import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.domain.UserOrders;
import com.example.domain.menu.PlaceMenu;
import com.example.pojo.dto.UserPlaceOrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Created by Dmitrij on 21.10.2015.
 */
@Service

public class UserServiceImpl implements UserService {

    @Autowired
    IDBBean dao;

    @Override
    public PlaceUser placeUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken))
            return dao.getUserByName(authentication.getName());
        else return null;
    }


    @Override
    public PlaceOwner placeOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken))
            return dao.getOwnerByName(authentication.getName());
        else return null;
    }

    @Override
    public boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream().parallel().anyMatch(c -> c.getAuthority().equals(role));
    }


    @Override
    public List<UserPlaceOrdersDTO> getOrderedServices(PlaceUser user) {
        long id = user.getId();
        List<UserPlaceOrdersDTO> placeOrders = new ArrayList<>();
        for (Place p : dao.getPlacesWithUserOrder(user)) {
            UserPlaceOrdersDTO order = new UserPlaceOrdersDTO();
            order.setPlace(p);
            order.setUserUserOrderses(dao.getUserPlaceOrders(id, p.getId()));
            placeOrders.add(order);
        }
        return placeOrders;
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public int newUserLikes(long id) {
        PlaceUser user = placeUser();
        Place p = dao.getPlaceById(id);
        if (!(dao.isPlaceUser(p.getId(), user.getId()) > 0)) dao.newPlaceLike(user, p);
        else dao.removeLike(user, id);
        return p.getPlaceUsers().size();
    }

    @Override
    public void newUserComment(String comment, int rating, long id) {
        if (rating > 5 || rating < 0 || comment.length() > 250)
            throw new IllegalArgumentException("Bad request");

        PlaceUser user = placeUser();
        UserOrders order = dao.getOrder(id);
        PlaceMenu placeMenu = order.getMenu();
        if (!(dao.countUserMenuRatings(placeMenu.getId(), user.getId()) > 0)) {
            dao.newPlaceMenuRating(comment, rating, user, placeMenu);
        } else {
            dao.updateMenuUserMenuRating(comment, rating, user, placeMenu);
        }
        dao.setOrderComplete(order, true);
        new Thread(() -> {
            dao.updateMenuFinalRating(placeMenu, dao.getMenuFinalRating(placeMenu));
            dao.getPlaceFinalRating(placeMenu.getMenuPlace());
        }).start();
    }
}
