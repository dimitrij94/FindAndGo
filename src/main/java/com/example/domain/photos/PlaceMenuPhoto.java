package com.example.domain.photos;

import com.example.domain.menu.PlaceMenu;

import javax.persistence.*;

/**
 * Created by Dmitrij on 30.10.2015.
 */
@Entity
@Table(name="place_menu_photo")
public class PlaceMenuPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    byte[] body;
    String name;

    @ManyToOne
    @JoinColumn(name = "menu")
    PlaceMenu menu;

    public PlaceMenuPhoto() {
    }

    public PlaceMenuPhoto(byte[] body) {
        this.body = body;
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
