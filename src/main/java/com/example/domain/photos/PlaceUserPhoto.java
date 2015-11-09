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
    private byte[]body;
    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;

    public PlaceUserPhoto(byte[] body) {
        this.body = body;
    }

    public PlaceUserPhoto() {
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

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }
}
