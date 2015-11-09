package com.example.domain.photos;

import com.example.domain.PlaceEvent;

import javax.persistence.*;

/**
 * Created by Dmitrij on 10.10.2015.
 */
@Entity
@Table(name = "place_event_photo")
public class PlaceEventPhoto {
    @Id
    @GeneratedValue
    Long id;
    String name;
    byte[]body;
    @ManyToOne
    @JoinColumn(name = "event")
    PlaceEvent event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public PlaceEvent getEvent() {
        return event;
    }

    public void setEvent(PlaceEvent event) {
        this.event = event;
    }
}
