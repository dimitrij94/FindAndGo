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
import com.example.domain.menu.PlaceMenuTags;
import com.example.domain.photos.PlaceEmployeePhoto;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.domain.photos.PlaceUserPhoto;
import com.example.domain.registration.VerificationToken;
import com.example.domain.photos.PlacePhoto;
import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.domain.users.employee.PlaceEmployee;
import com.example.interfaces.PhotoCotainable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dmitrij
 */

public interface IDBBean {

    Place getPlaceById(long id);

    Place getOwnerPlaceById(long id, PlaceOwner owner);

    PlaceMenu getMenuById(long id);

    PlaceUser authorization(String name, String pass);


    void addNewPlace(Place place, PlaceAddress placeAddress, PlaceOwner owner, PlaceSpeciality speciality);

    List<Place> getMainPlaces();


    long checkCredentials(String email, String userName);

    void newToken(VerificationToken tokens, PlaceUser user);

    VerificationToken findToken(String token);

    PlaceUser getUserByName(String ownerName);


    void deleteToken(VerificationToken token);

    byte[] getPlaceImageBodyByName(long id, String name);

    @Cacheable("photo-cache")
    PlacePhoto getPlacePhotoByName(String name, long id);

    void newMenu(PlaceMenu menu, Place place, List<PlaceMenuTags> tags);

    void addMenuPhoto(byte[] body, PhotoCotainable domain, String name);

    void newPlaceMenuService(PlaceMenu menu, PlaceMenuOptionalService service);

    byte[] getMenuImageBodyByName(long id, String name);

    PlaceMenuPhoto getMenuPhotoByName(String name, long id);

    void addPlacePhoto(byte[] body, PhotoCotainable domain, String name);

    PlaceUser registration(PlaceUser user, UserAddress address);

    void newPlaceMenuOptionalService(PlaceMenuOptionalService service, PlaceMenu menu);

    PlaceMenuOptionalService getMenuServicesById(Long l);

    @Transactional
    UserOrders newOrder(PlaceUser user, Place place, PlaceMenu menu, List<Long> servicesList, PlaceEmployee employee);

    List<UserOrders> getUserPlaceOrders(long userId, long placeId);

    List<Place> getPlacesWithUserOrder(PlaceUser user);

    boolean isMenuFromPlace(PlaceMenu menu, Place place);

    Place getOwnerPlace(long placeId, PlaceOwner user);

    PlaceUser getUserById(long i);

    void newPlaceLike(PlaceUser user, Place place);

    void removeLike(PlaceUser user, long placeId);

    public byte[] getUserPhotoBodyByName(String name, long id);

    PlaceUserPhoto getUserPhotoByName(String name, long id);

    long countUserMenuRatings(long menuId, long userId);

    void newPlaceMenuRating(String comment, int rating, PlaceUser user, PlaceMenu placeMenu);

    void deleteComment(PlaceUser user, PlaceMenu placeMenu);

    PlaceSpeciality getPlaceSpeciality(String specialization);

    boolean isUserUsedPlace(Place p, PlaceUser user);



    long isPlaceUser(Long id, Long id1);

    int getPlaceFinalRating(Place place);

    UserOrders getOrder(long id);

    int getMenuFinalRating(PlaceMenu placeMenu);

    void updatePlaceRating(Place place, int finalRating);

    void updateMenuFinalRating(PlaceMenu placeMenu, int menuFinalRating);

    void setOrderComplete(UserOrders order, boolean b);

    long isUserLikedPlace(PlaceUser user, Place p);

    PlaceOwner getOwnerByName(String s);

    void updateMenuUserMenuRating(String comment, int rating, PlaceUser user, PlaceMenu placeMenu);

    List<UserOrders> getEmployeeTodayOrders(PlaceEmployee employee, LocalDate date);

    List getEmployeeTodayPauses(PlaceEmployee employee, LocalDateTime localDate);

    List<PlaceMenu> getBestMenus(int from, int to);

    PlaceEmployee getEmployeeById(long employeeID);

    UserOrders activateOrder(UserOrders order);

    UserOrders findOrderById(long id, PlaceUser user);

    List<PlaceMenu> findMenuWithTag(List<Long> tagsId, int startFrom, int endAt);

    List<PlaceMenuTags> findTagsByName(String t);

    PlaceMenuTags findTagByName(String name);

    PlaceMenuTags newTag(String t);

    PlaceEmployee getEmployeeByName(String s);

    PlaceEmployeePhoto getEmployeePhotoByName(String name, long id);

    void addEmployeePhoto(byte[] body, PhotoCotainable domain, String name);

    void addPlaceUserPhoto(byte[] body, PhotoCotainable domain, String name);
}
