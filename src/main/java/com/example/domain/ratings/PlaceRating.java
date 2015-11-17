package com.example.domain.ratings;

import com.example.domain.Place;
import com.example.domain.PlaceUser;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * Created by Dimitrij on 22.08.2015.
 */
@Entity
public class PlaceRating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "place")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;

    public PlaceRating rating(int rating){
        this.rating=rating;
        return this;
    }
    public PlaceRating place(Place place){
        this.place=place;
        return this;
    }
    public PlaceRating user(PlaceUser user){
        this.user=user;
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

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }


}
