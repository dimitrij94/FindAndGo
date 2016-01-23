package com.example.domain.owner;

import com.example.domain.place.Place;
import com.example.interfaces.Authenticational;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 25.11.2015.
 */
@Entity
@Table(name = "place_owner")
public class PlaceOwner implements Authenticational {
    @Id
    @GeneratedValue
    private long id;

    private String email;
    private String name;
    private String password;
    private boolean enabled;
    @OneToMany(mappedBy = "placeOwner")
    private List<Place> places;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getAuthority() {
        return "ROLE_OWNER";
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> place) {
        this.places = place;
    }
}
