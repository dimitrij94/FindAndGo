/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.domain.menu;


import com.example.domain.UserOrders;
import com.example.domain.Place;
import com.example.domain.ratings.PlaceMenuRating;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.pojo.dto.MenuDTO;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Dmitrij
 */
@Entity
public class PlaceMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    private int durationH;
    private int durationM;

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
        this.durationH=menuDTO.getHours();
        this.durationM=menuDTO.getMinutes();
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

    public Long getId() {
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

    public int getDurationH() {
        return durationH;
    }

    public void setDurationH(int durationH) {
        this.durationH = durationH;
    }

    public int getDurationM() {
        return durationM;
    }

    public void setDurationM(int durationM) {
        this.durationM = durationM;
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

        return id.equals(menu.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
