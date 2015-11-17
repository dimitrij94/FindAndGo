package com.example.services.userservice;

import com.example.dao.IDBBean;
import com.example.domain.Place;
import com.example.domain.PlaceUser;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.ratings.PlaceMenuRating;
import com.example.pojo.dto.UserPlaceOrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
    public Place findPlaceByOwnerId(PlaceUser user, long id) {
        for (Place p : user.getOwnerPlaces()) {
            if (p.getId() == id) return p;
        }
        return null;
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
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Override
    public int newUserLikes(long id, HttpServletResponse response) {
        PlaceUser user = placeUser();
        Place p = dao.getPlaceById(id);
        if (p.getPlaceUsers().size() > 0) dao.newPlaceLike(user, p);
        else dao.removeLike(user, id);
        response.setStatus(HttpStatus.OK.value());
        return p.getPlaceUsers().size();
    }

    @Override
    public void newUserComment(String comment, int rating, long id) {
        PlaceUser user = placeUser();
        PlaceMenu placeMenu = dao.getMenuById(id);
        if (dao.countUserMenuRatings(user.getId(), placeMenu.getId()) == 0)
            dao.newPlaceMenuRating(comment, rating, user, placeMenu);
        else {
            dao.deleteComment(user, placeMenu);
            dao.newPlaceMenuRating(comment, rating, user, placeMenu);
        }
        calculateMenuFinalRating(placeMenu);
    }

    private int calculateMenuFinalRating(PlaceMenu placeMenu) {
        int finalRating = 0;
        for (PlaceMenuRating r : placeMenu.getMenuRatings())
            finalRating += r.getRating();

        finalRating = finalRating / placeMenu.getMenuRatings().size();
        placeMenu.setMenuFinalRating(finalRating);
        return (finalRating);
    }
}
