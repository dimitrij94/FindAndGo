/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.domain.place;


import com.example.constants.PlaceSpeciality;
import com.example.domain.UserOrders;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.interfaces.PhotoCotainable;
import com.example.pojo.dto.PlaceDTO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Dmitrij
 */
@Entity
public class Place implements PhotoCotainable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String placeName;

    private String placeDescription;
    private int timeOffset;
    private int placeFinalRating;
    private int placeFollowersNum;

    @Enumerated(EnumType.STRING)
    PlaceSpeciality speciality;

    @OneToMany(mappedBy = "menuPlace")
    private List<PlaceMenu> placeMenu;

    @ManyToMany(mappedBy = "userPlaces")
    private List<PlaceUser> placeUsers;

    @OneToMany(mappedBy = "place")
    private List<PlacePhoto> placePhotos;

    @ManyToOne
    @JoinColumn(name = "placeOwner")
    private PlaceOwner placeOwner;

    @OneToOne(mappedBy = "place")
    private PlaceAddress address;

    @OneToMany(mappedBy = "place")
    private List<UserOrders> userOrderses;

    @OneToMany(mappedBy = "place")
    List<PlaceSchedule> placeSchedules;

    public Place() {
    }

    public Place(HttpServletRequest request) {
        this.placeName = request.getParameter("placeName");
        this.placeDescription = request.getParameter("placeDescription");
    }

    public Place(PlaceDTO place) {
        this.placeName = place.getName();
        this.placeDescription = place.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public int getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(int timeOffset) {
        this.timeOffset = timeOffset;
    }

    public int getPlaceFinalRating() {
        return placeFinalRating;
    }

    public void setPlaceFinalRating(int placeFinalRating) {
        this.placeFinalRating = placeFinalRating;
    }

    public int getPlaceFollowersNum() {
        return placeFollowersNum;
    }

    public void setPlaceFollowersNum(int placeFollowersNum) {
        this.placeFollowersNum = placeFollowersNum;
    }

    public PlaceSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(PlaceSpeciality speciality) {
        this.speciality = speciality;
    }

    public List<PlaceMenu> getPlaceMenu() {
        return placeMenu;
    }

    public void setPlaceMenu(List<PlaceMenu> placeMenu) {
        this.placeMenu = placeMenu;
    }

    public List<PlaceUser> getPlaceUsers() {
        return placeUsers;
    }

    public void setPlaceUsers(List<PlaceUser> placeUsers) {
        this.placeUsers = placeUsers;
    }

    public List<PlacePhoto> getPlacePhotos() {
        return placePhotos;
    }

    public void setPlacePhotos(List<PlacePhoto> placePhotos) {
        this.placePhotos = placePhotos;
    }


    public PlaceOwner getPlaceOwner() {
        return placeOwner;
    }

    public void setPlaceOwner(PlaceOwner placeOwner) {
        this.placeOwner = placeOwner;
    }

    public PlaceAddress getAddress() {
        return address;
    }

    public void setAddress(PlaceAddress address) {
        this.address = address;
    }

    public List<UserOrders> getUserOrderses() {
        return userOrderses;
    }

    public void setUserOrderses(List<UserOrders> userOrderses) {
        this.userOrderses = userOrderses;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PlaceSchedule> getPlaceSchedules() {
        return placeSchedules;
    }

    public void setPlaceSchedules(List<PlaceSchedule> placeSchedules) {
        this.placeSchedules = placeSchedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        return id == place.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


}
