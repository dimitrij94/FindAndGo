package com.example.domain.ratings;

import com.example.domain.menu.PlaceMenu;
import com.example.domain.PlaceUser;

import javax.persistence.*;

@Entity
public class PlaceMenuRating {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private float rating;

    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;

    @ManyToOne
    @JoinColumn(name="menu")
    private PlaceMenu menu;

    public PlaceMenuRating rating(float rating){
        this.rating=rating;
        return this;
    }
    public PlaceMenuRating user(PlaceUser user){
        this.user=user;
        return this;
    }

    public PlaceMenuRating menu (PlaceMenu menu){
        this.menu=menu;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
