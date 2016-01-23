package com.example.domain.users;


import com.example.pojo.dto.AddressDTO;

import javax.persistence.*;

/**
 * Created by Dmitrij on 22.09.2015.
 */
@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String latitude;
    private String longitude;

    @OneToOne
    @JoinColumn(name = "user_id")
    private PlaceUser user;

    public UserAddress() {
    }

    public UserAddress(AddressDTO form) {
        this.latitude = Float.toString(form.getLatitude());
        this.longitude = Float.toString(form.getLongitude());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }
}
