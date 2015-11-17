package com.example.domain;

import javax.persistence.*;

/**
 * Created by Dmitrij on 17.11.2015.
 */

@Entity
@Table(name="place_speciality")
public class PlaceSpeciality {
    @Id
    @GeneratedValue
    long id;
    String speciality;
    @OneToOne(mappedBy = "placeSpeciality")
    Place place;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
