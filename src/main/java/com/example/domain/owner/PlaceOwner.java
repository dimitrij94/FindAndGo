package com.example.domain.owner;

import com.example.domain.place.Place;
import com.example.domain.registration.OwnerVerificationToken;
import com.example.interfaces.Authenticational;
import com.example.pojo.dto.OwnerDTO;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 25.11.2015.
 */
@Entity
@Table(name = "place_owner")
public class PlaceOwner implements Authenticational {

    @Transient
    private final static String authority = "ROLE_OWNER";

    @Id
    @GeneratedValue
    private long id;

    private String email;
    private String name;
    private String password;
    private boolean enabled;

    @OneToMany(mappedBy = "placeOwner")
    private List<Place> places;

    @OneToOne(mappedBy = "owner")
    private OwnerVerificationToken token;

    public PlaceOwner(OwnerDTO ownerDTO) {
        this.email = ownerDTO.getEmail();
        this.name = ownerDTO.getName();
        this.password = ownerDTO.getPassword();
    }

    public PlaceOwner() {
    }

    @Override
    public String getAuthority() {
        return "ROLE_OWNER";
    }

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> place) {
        this.places = place;
    }

    public OwnerVerificationToken getToken() {
        return token;
    }

    public void setToken(OwnerVerificationToken token) {
        this.token = token;
    }
}
