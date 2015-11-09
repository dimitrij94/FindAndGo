package com.example.domain.addresses;

import com.example.domain.Place;
import com.example.pojo.dto.AddressDTO;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.persistence.*;

/**
 * Created by Dmitrij on 01.10.2015.
 */
@Entity
@Table(name = "place_address")
public class PlaceAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    float latitude;

    float longitude;

    String fullAddress;

    public PlaceAddress(DefaultMultipartHttpServletRequest dmhsRequest) {
        this.longitude = Float.valueOf(dmhsRequest.getParameter("longitude"));
        this.latitude = Float.valueOf(dmhsRequest.getParameter("latitude"));
        this.fullAddress=dmhsRequest.getParameter("fullAddress");
    }

    public PlaceAddress() {
    }

    @OneToOne
    @JoinColumn(name = "place_id")
    Place place;

    public PlaceAddress(AddressDTO address) {
        this.latitude=address.getLatitude();
        this.longitude=address.getLongitude();
        this.fullAddress=address.getFullAddress();
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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
