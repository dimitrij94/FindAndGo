/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.dao;


import com.example.domain.*;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.addresses.PlaceAddress;
import com.example.domain.addresses.UserAddress;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.domain.registration.Authorities;
import com.example.domain.registration.VerificationToken;
import com.example.domain.photos.PlacePhoto;
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


    void addNewPlace(Place place, PlaceAddress placeAddress, PlaceUser owner, PlaceSpeciality speciality);

    Authorities getAuthority(String name);

    ArrayList<PlaceEvent> getMainEvents();

    List<Place> getMainPlaces();


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

    void newPlaceLike(PlaceUser user, Place place);

    void removeLike(PlaceUser user, long placeId);

    public byte[] getUserPhoto(long id, String name);

    long countUserMenuRatings(long menuId, long userId);

    void newPlaceMenuRating(String comment, int rating, PlaceUser user, PlaceMenu placeMenu);

    void deleteComment(PlaceUser user, PlaceMenu placeMenu);

    PlaceSpeciality getPlaceSpeciality(String specialization);

    boolean isUserUsedPlace(Place p, PlaceUser user);

    long countUserPlaceRatings(long pId, Long id);


    void newPlaceRating(Place place, PlaceUser user, int rating);

    void deleteUserPlaceRating(Long id, long pId);

    long isPlaceUser(Long id, Long id1);

    int getPlaceFinalRating(Place place);

    UserOrders getOrder(long id);

    int getMenuFinalRating(PlaceMenu placeMenu);

    void updatePlaceRating(Place place, int finalRating);

    void updateMenuFinalRating(PlaceMenu placeMenu, int menuFinalRating);

    void setOrderComplete(UserOrders order, boolean b);

    long isUserLikedPlace(PlaceUser user, Place p);
}
