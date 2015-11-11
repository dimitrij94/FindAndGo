package com.example.domain;

import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 08.11.2015.
 */
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "place")
    Place place;

    @ManyToOne
    @JoinColumn(name = "user")
    PlaceUser user;

    @ManyToOne
    @JoinColumn(name = "menu")
    PlaceMenu menu;

    @ManyToMany(mappedBy = "orders")
    List<PlaceMenuOptionalService> services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }

    public PlaceMenu getMenu() {
        return menu;
    }

    public void setMenu(PlaceMenu menu) {
        this.menu = menu;
    }

    public List<PlaceMenuOptionalService> getServices() {
        return services;
    }

    public void setServices(List<PlaceMenuOptionalService> services) {
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id.equals(order.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
