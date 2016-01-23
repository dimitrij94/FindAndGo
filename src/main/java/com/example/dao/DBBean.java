//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.dao;

import com.example.domain.UserOrders;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.place.Place;
import com.example.domain.place.PlacePhoto;
import com.example.domain.users.PlaceUser;
import com.example.domain.users.PlaceUserPhoto;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public abstract class DBBean {

    @PersistenceContext
    protected EntityManager em;


    protected List<PlaceUserPhoto> setUserPhotosAsList(List<PlaceUserPhoto> list,
                                                     PlaceUserPhoto photo) {
        if (list == null) {
            return Collections.singletonList(photo);
        } else {
            list.add(photo);
            return list;
        }
    }


    protected void setPlaceUsers(List<PlaceUser> users, Place p, PlaceUser user) {
        if (users == null) {
            p.setPlaceUsers(Collections.singletonList(user));
        } else {
            users.add(user);
        }
        em.flush();
    }

    protected List<PlacePhoto> savePlacePhotoAsList(List<PlacePhoto> list, PlacePhoto photo) {
        if (list == null) {
            ArrayList<PlacePhoto> singeltonList = new ArrayList<>(2);
            singeltonList.add(photo);
            return singeltonList;
        } else {
            list.add(photo);
            return list;
        }
    }


    protected List<UserOrders> getOrdersList(UserOrders userOrders, List<UserOrders> list) {
        if (list == null) return Collections.singletonList(userOrders);
        else {
            list.add(userOrders);
            return list;
        }
    }


    protected <T> List<T> setAsList(List<T> list, T object) {
        if (list == null) {
            return Collections.singletonList(object);
        } else {
            list.add(object);
            return list;
        }
    }

    protected List<Place> getUserPlacesAsList(Place p, List<Place> list) {
        if (list == null) return Collections.singletonList(p);
        else {
            list.add(p);
            return list;
        }

    }

    protected List<PlaceMenuOptionalService> getServiceAsList(PlaceMenuOptionalService s,
                                                              List<PlaceMenuOptionalService> list) {
        if (list == null) return Collections.singletonList(s);
        else {
            list.add(s);
            return list;
        }
    }


}
