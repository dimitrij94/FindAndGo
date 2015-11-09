package com.example.services.userservice;

import com.example.dao.IDBBean;
import com.example.domain.Place;
import com.example.domain.PlaceUser;
import com.example.services.authorization.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        if (authentication.isAuthenticated()&&!(authentication instanceof AnonymousAuthenticationToken))
            return dao.getUserByName(authentication.getName());
        else return null;
    }

    @Override
    public Place findPlaceByOwnerId(PlaceUser user, long id) {
        for(Place p:user.getOwnerPlaces()){
            if(p.getId()==id)return p;
        }
        return null;
    }


}
