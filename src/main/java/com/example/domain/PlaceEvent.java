package com.example.domain;

import com.example.domain.photos.PlaceEventPhoto;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 01.10.2015.
 */
@Entity
@Table(name = "event")
public class PlaceEvent implements Comparable<PlaceEvent> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String eventName;
    String eventDescription;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            name = "event_users")
    List<PlaceUser> users;

    int followersNum;

    @ManyToOne
    @JoinColumn(name = "place_id")
    Place place;

    @OneToMany(mappedBy = "event")
    List<PlaceEventPhoto> photos;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public List<PlaceUser> getUsers() {
        return users;
    }

    public void setUsers(List<PlaceUser> users) {
        this.users = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlaceEventPhoto> getPhoto() {
        return photos;
    }

    public void setPhoto(List<PlaceEventPhoto> photo) {
        this.photos = photo;
    }

    public List<PlaceEventPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PlaceEventPhoto> photos) {
        this.photos = photos;
    }

    public int getFollowersNum() {
        return followersNum;
    }

    public void setFollowersNum(int followersNum) {
        this.followersNum = followersNum;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(PlaceEvent o) {
        int r = this.users.size() - o.getUsers().size();
        if (r != 0) {
            if (r > 0)
                return 1;
            return -1;
        }
        return 0;
    }
}
