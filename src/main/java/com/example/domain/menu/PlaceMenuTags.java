package com.example.domain.menu;


import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 09.12.2015.
 */
@Entity
@Table(name = "place_menu_tags")
public class PlaceMenuTags {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
            name = "menu_tags")
    List<PlaceMenu> menus;

    public PlaceMenuTags(String t) {
        this.name = t;
    }

    public PlaceMenuTags() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlaceMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<PlaceMenu> menus) {
        this.menus = menus;
    }
}

