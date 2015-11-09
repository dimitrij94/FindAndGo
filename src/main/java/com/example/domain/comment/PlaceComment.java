package com.example.domain.comment;

import com.example.domain.Place;
import com.example.domain.PlaceUser;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 10.10.2015.
 */
@Entity
@Table(name = "place_comment")
public class PlaceComment {
    @Id
    @GeneratedValue
    private Long id;
    String comment;
    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id")
            , name = "comment_suporters")
    private List<PlaceUser> supporters;
    @ManyToOne
    @JoinColumn(name = "place")
    private Place place;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }

    public List<PlaceUser> getSupporters() {
        return supporters;
    }

    public void setSupporters(List<PlaceUser> supporters) {
        this.supporters = supporters;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
