package com.example.domain.place;

import com.example.pojo.dto.AddressDTO;

import javax.persistence.*;

/**
 * Created by Dmitrij on 01.10.2015.
 */
@Entity
@Table(name = "place_address")
public class PlaceAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private    float latitude;

    private   float longitude;


    public PlaceAddress() {
    }

    @OneToOne
    @JoinColumn(name = "place_id")
    private  Place place;

    public PlaceAddress(AddressDTO address) {
        this.latitude=address.getLatitude();
        this.longitude=address.getLongitude();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }


}
