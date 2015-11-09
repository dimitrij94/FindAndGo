package com.example.domain.photos;

import com.example.domain.Place;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;

/**
 * Created by Dmitrij on 10.07.2015.
 */
@Entity
@Table(name="place_photo")
public class PlacePhoto {
    @ManyToOne
    @JoinColumn(name = "place")
    private Place place;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private byte[]body;
    private String name;

    public PlacePhoto(byte[] body) {
        this.body = body;
    }

    public PlacePhoto(){

    }


    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
