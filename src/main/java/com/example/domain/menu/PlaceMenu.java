/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.domain.menu;


import com.example.domain.UserOrders;
import com.example.domain.place.Place;
import com.example.interfaces.PhotoCotainable;
import com.example.pojo.dto.MenuDTO;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;

/**
 *
 * @author Dmitrij
 */
@Entity
public class PlaceMenu implements Serializable, PhotoCotainable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String menuName;
    private String menuDescription;
    private int menuPrice;
    private int menuFinalRating;

    @OneToMany(mappedBy = "menu")
    private List<PlaceMenuRating> menuRatings;

    @ManyToOne
    @JoinColumn(name="menuPlace")
    private Place menuPlace;

    @OneToMany(mappedBy = "menu")
    private List<PlaceMenuPhoto> photo;

    @OneToMany(mappedBy = "menu")
    private  List<PlaceMenuOptionalService>services;

    @OneToMany(mappedBy = "menu")
    private List<UserOrders> userOrderses;

    private long durationMinutes;
    @ManyToMany(mappedBy = "menus")
    private List<PlaceMenuTags>menuTags;

    public PlaceMenu() {
    }

    public PlaceMenu(DefaultMultipartHttpServletRequest dmhsRequest) {
        this.menuName=dmhsRequest.getParameter("menuName");
        this.menuDescription=dmhsRequest.getParameter("menuDescription");
        this.menuPrice=Integer.valueOf(dmhsRequest.getParameter("menuPrice"));
    }

    public PlaceMenu(MenuDTO menuDTO) {
        this.menuName=menuDTO.getName();
        this.menuDescription=menuDTO.getDescription();
        this.menuPrice=menuDTO.getPrice();
        this.durationMinutes =(Duration.ofHours(menuDTO.getHours()).plus(
                Duration.ofMinutes(menuDTO.getMinutes())).toMinutes());
    }

    public List<PlaceMenuTags> getMenuTags() {
        return menuTags;
    }

    public void setMenuTags(List<PlaceMenuTags> menuTags) {
        this.menuTags = menuTags;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getMenuFinalRating() {
        return menuFinalRating;
    }

    public void setMenuFinalRating(int menuFinalRating) {
        this.menuFinalRating = menuFinalRating;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Place getMenuPlace() {
        return menuPlace;
    }
    
    public void setMenuPlace(Place place) {
        this.menuPlace = place;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String serviceName) {
        this.menuName = serviceName;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String serviceDescription) {
        this.menuDescription = serviceDescription;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public List<PlaceMenuRating> getMenuRatings() {
        return menuRatings;
    }

    public void setMenuRatings(List<PlaceMenuRating> menuRatings) {
        this.menuRatings = menuRatings;
    }

    public List<PlaceMenuPhoto> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PlaceMenuPhoto> photo) {
        this.photo = photo;
    }

    public List<PlaceMenuOptionalService> getServices() {
        return services;
    }

    public void setServices(List<PlaceMenuOptionalService> services) {
        this.services = services;
    }

    public List<UserOrders> getUserOrderses() {
        return userOrderses;
    }

    public void setUserOrderses(List<UserOrders> userOrderses) {
        this.userOrderses = userOrderses;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(long duration) {
        this.durationMinutes = duration;
    }

    @Override
    public String toString() {
        return "entetis.PlaceMenu[ id=" + id + " ]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceMenu menu = (PlaceMenu) o;

        return id == menu.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
