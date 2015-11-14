/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.dao;


import com.example.domain.UserOrders;
import com.example.domain.Place;
import com.example.domain.PlaceEvent;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.addresses.PlaceAddress;
import com.example.domain.addresses.UserAddress;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.domain.registration.Authorities;
import com.example.domain.registration.VerificationToken;
import com.example.domain.photos.PlacePhoto;
import com.example.domain.PlaceUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitrij
 */

public interface IDBBean {

    Place getPlaceById(long id);

    Place getOwnerPlaceById(long id, PlaceUser user);

    PlaceMenu getMenuById(long id);

    PlaceUser authorization(String name, String pass);


    void addNewPlace(Place place, PlaceAddress placeAddress, PlaceUser owner);

    ArrayList<PlaceEvent> getMainEvents();

    List<Place> getMainPlaces();


    /*
        @Override
        public long getDbIdFromUserId(long userId){
           return ((ActiveUserConnections)(em.createQuery("SELECT e from ActiveUserConnections e WHERE e.userId=:userId").setParameter("userId",userId).getSingleResult())).getDbId();
        }
    */
    Integer changePlaceMenuRating(long placeMenuId, long userId, int rating);

    Integer changePlaceRating(long placeId, long userId, int rating);

    long checkCredentials(String email, String userName);

    void newToken(VerificationToken tokens, PlaceUser user);

    VerificationToken findToken(String token);

    PlaceUser getUserByName(String ownerName);

    void grandUserAuthorities(PlaceUser user, Authorities authorities);

    void deleteToken(VerificationToken token);

    byte[] getPlaceMainImage(long id);

    byte[] getPlaceSmallImage(long id);

    void newMenu(PlaceMenu menu, Place place);

    void saveMenuPhoto(PlaceMenu menu, PlaceMenuPhoto photo);

    void newPlaceMenuService(PlaceMenu menu, PlaceMenuOptionalService service);

    byte[] getMenuSmallImage(long id);

    void addPlacePhoto(PlacePhoto photo, Place place);

    PlaceUser registration(PlaceUser user, UserAddress address);

    void newPlaceMenuOptionalService(PlaceMenuOptionalService service, PlaceMenu menu);

    PlaceMenuOptionalService getMenuServicesById(Long l);

    @Transactional
    void newOrder(PlaceUser user, Place place, PlaceMenu menu, List<Long> servicesList);

    List<UserOrders> getUserPlaceOrders(long userId, long placeId);

    List<Place> getPlacesWithUserOrder(PlaceUser user);

    boolean isMenuFromPlace(PlaceMenu menu, Place place);

    Place getOwnerPlace(long placeId, PlaceUser user);

    PlaceUser getUserById(long i);

    int countLiked(Long userId, Long placeId);

    void newPlaceLike(PlaceUser user, long id);

    void removeLike(PlaceUser user, long placeId);

    public byte[] getUserPhoto(long id, String name);
}
