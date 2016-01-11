package com.example.domain.photos;

import com.example.constants.image.sizes.ImageSize;
import com.example.constants.image.sizes.MenuImageSizes;
import com.example.dao.IDBBean;
import com.example.domain.menu.PlaceMenu;
import com.example.interfaces.PhotoCotainable;
import com.example.functional.photos.SavePhotoFunction;
import com.example.interfaces.Scaleble;

import javax.persistence.*;

/**
 * Created by Dmitrij on 30.10.2015.
 */
@Entity
@Table(name = "place_menu_photo")
public class PlaceMenuPhoto implements Scaleble {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    byte[] body;
    private   String name;

    @ManyToOne
    @JoinColumn(name = "menu")
    private   PlaceMenu menu;

    public PlaceMenuPhoto() {
    }

    public PlaceMenuPhoto(byte[] body, String name,PlaceMenu menu) {
        this.name = name;
        this.body = body;
        this.menu = menu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


    public PlaceMenu getMenu() {
        return menu;
    }

    public void setMenu(PlaceMenu menu) {
        this.menu = menu;
    }
}
