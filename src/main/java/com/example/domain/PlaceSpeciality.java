package com.example.domain;

import javax.persistence.*;
import java.util.List;

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
    @ManyToMany(mappedBy = "placeSpeciality")
    List<Place> place;

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

    public List<Place> getPlace() {
        return place;
    }

    public void setPlace(List<Place> place) {
        this.place = place;
    }
}
