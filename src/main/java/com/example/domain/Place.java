/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.domain;


import com.example.domain.menu.PlaceMenu;
import com.example.domain.ratings.PlaceRating;
import com.example.domain.addresses.PlaceAddress;
import com.example.domain.comment.PlaceComment;
import com.example.domain.photos.PlacePhoto;
import com.example.pojo.dto.PlaceDTO;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author Dmitrij
 */
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String placeName;

    private String placeDescription;

    private int placeFinalRating;
    private int placeRatingsNum;
    private int placeFollowersNum;
    private String placeSpeciality;

    @OneToMany(mappedBy = "place")
    private List<PlaceRating> placeRatings;

    @OneToMany(mappedBy= "menuPlace")
    private List<PlaceMenu>placeMenu;

    @ManyToMany(mappedBy = "userPlaces")
    private List<PlaceUser> placeUsers;

    @OneToMany(mappedBy = "place")
    private List<PlacePhoto> placePhotos;

    @OneToMany(mappedBy = "place")
    List<PlaceEvent>placeEvents;

    @OneToMany(mappedBy = "place")
    List<PlaceComment>placeComments;

    @ManyToOne
    @JoinColumn(name = "placeOwner")
    PlaceUser placeOwner;

    @OneToOne(mappedBy = "place")
    PlaceAddress address;

    @OneToMany(mappedBy = "place")
    List<UserOrders> userOrderses;

    public Place() {
    }

    public Place(HttpServletRequest request){
        this.placeName=request.getParameter("placeName");
        this.placeDescription=request.getParameter("placeDescription");
        this.placeSpeciality=request.getParameter("placeSpeciality");
    }

    public Place(PlaceDTO place){
        this.placeName=place.getName();
        this.placeDescription=place.getDescription();
        this.placeSpeciality=place.getSpecialization();
    }

    public Long getId() {
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

    public int getPlaceFinalRating() {
        return placeFinalRating;
    }

    public void setPlaceFinalRating(int placeFinalRating) {
        this.placeFinalRating = placeFinalRating;
    }

    public int getPlaceRatingsNum() {
        return placeRatingsNum;
    }

    public void setPlaceRatingsNum(int placeRatingsNum) {
        this.placeRatingsNum = placeRatingsNum;
    }

    public int getPlaceFollowersNum() {
        return placeFollowersNum;
    }

    public void setPlaceFollowersNum(int placeFollowersNum) {
        this.placeFollowersNum = placeFollowersNum;
    }

    public String getPlaceSpeciality() {
        return placeSpeciality;
    }

    public void setPlaceSpeciality(String placeSpeciality) {
        this.placeSpeciality = placeSpeciality;
    }

    public List<PlaceRating> getPlaceRatings() {
        return placeRatings;
    }

    public void setPlaceRatings(List<PlaceRating> placeRatings) {
        this.placeRatings = placeRatings;
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

    public List<PlaceEvent> getPlaceEvents() {
        return placeEvents;
    }

    public void setPlaceEvents(List<PlaceEvent> placeEvents) {
        this.placeEvents = placeEvents;
    }

    public List<PlaceComment> getPlaceComments() {
        return placeComments;
    }

    public void setPlaceComments(List<PlaceComment> placeComments) {
        this.placeComments = placeComments;
    }

    public PlaceUser getPlaceOwner() {
        return placeOwner;
    }

    public void setPlaceOwner(PlaceUser placeOwner) {
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        return id.equals(place.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
