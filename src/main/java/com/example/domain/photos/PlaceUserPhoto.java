package com.example.domain.photos;

import com.example.domain.PlaceUser;

import javax.persistence.*;

/**
 * Created by Dimitrij on 22.08.2015.
 */
@Entity
public class PlaceUserPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private byte[] body;
    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;
    String name;

    public PlaceUserPhoto(byte[] body) {
        this.body = body;
    }

    public PlaceUserPhoto() {
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }

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
}
