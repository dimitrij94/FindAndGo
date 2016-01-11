package com.example.domain.photos;

import com.example.constants.image.sizes.ImageSize;
import com.example.constants.image.sizes.UserImageSizes;
import com.example.dao.IDBBean;
import com.example.domain.users.PlaceUser;
import com.example.interfaces.PhotoCotainable;
import com.example.functional.photos.SavePhotoFunction;
import com.example.interfaces.Scaleble;

import javax.persistence.*;

/**
 * Created by Dimitrij on 22.08.2015.
 */
@Entity
public class PlaceUserPhoto implements Scaleble{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private byte[] body;
    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;
    private  String name;

    public PlaceUserPhoto(byte[] body) {
        this.body = body;
    }

    public PlaceUserPhoto() {
    }

    public PlaceUserPhoto(byte[] body, PlaceUser user, String name) {
        this.body = body;
        this.user = user;
        this.name = name;
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
