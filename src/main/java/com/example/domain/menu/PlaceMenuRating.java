package com.example.domain.menu;

import com.example.domain.users.PlaceUser;

import javax.persistence.*;

@Entity
public class PlaceMenuRating {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private int rating;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;

    @ManyToOne
    @JoinColumn(name="menu")
    private PlaceMenu menu;

    public PlaceMenuRating rating(int rating){
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

    public PlaceMenuRating comment(String comment){
        this.comment=comment;
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

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }

    public PlaceMenu getMenu() {
        return menu;
    }

    public void setMenu(PlaceMenu menu) {
        this.menu = menu;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
