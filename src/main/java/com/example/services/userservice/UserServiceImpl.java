package com.example.services.userservice;

import com.example.dao.IDBBean;
import com.example.domain.Place;
import com.example.domain.PlaceUser;
import com.example.pojo.dto.UserPlaceOrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}
