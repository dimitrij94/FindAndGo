package com.example.graph.user;

import com.example.graph.Person;
import com.example.graph.PlaceUserOrder;
import com.example.graph.photos.PlaceUserPhoto;
import com.example.graph.place.Place;
import com.example.json_views.UserJsonView;
import com.example.pojo.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonView;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitrij on 20.02.2016.
 */
@NodeEntity
public class PlaceUser extends Person{

    public transient static final String ORDERS = "ORDERS";
    public transient static final String VERIFICATION_TOKEN = "VERIFICATION_TOKEN";
    public transient static final String PHOTO = "PHOTO";
    public transient static final String MEMBER_OF = "MEMBER_OF";

    @RelatedTo(type = MEMBER_OF, direction = Direction.OUTGOING)
    Set<Place> places;

    @JsonView(UserJsonView.class)
    @RelatedTo(type = PHOTO, direction = Direction.OUTGOING)
    Set<PlaceUserPhoto> photos;

    @RelatedTo(type = ORDERS, direction = Direction.OUTGOING)
    Set<PlaceUserOrder> orders;

    @RelatedTo(type = VERIFICATION_TOKEN, direction = Direction.OUTGOING)
    PlaceUserVerificationToken token;

    public PlaceUser(UserDTO userDTO) {
        this.setUserName(userDTO.getName());
        setEmail(userDTO.getUserEmail());
        setPassword(userDTO.getUserPass());
    }

    public PlaceUser addMemebership(Place place) {
        if (this.places == null) places = new HashSet<Place>();
        this.places.add(place);
        return this;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public Set<PlaceUserPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<PlaceUserPhoto> photos) {
        this.photos = photos;
    }

    public Set<PlaceUserOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<PlaceUserOrder> orders) {
        this.orders = orders;
    }

    public PlaceUserVerificationToken getToken() {
        return token;
    }

    public void setToken(PlaceUserVerificationToken token) {
        this.token = token;
    }
}
