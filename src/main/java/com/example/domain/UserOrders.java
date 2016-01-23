package com.example.domain;

import com.example.domain.employee.PlaceEmployee;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Dmitrij on 08.11.2015.
 */
@Entity
@Table(name = "user_order")
public class UserOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "user")
    private PlaceUser user;

    @ManyToOne
    @JoinColumn(name = "menu")
    private PlaceMenu menu;

    @ManyToOne
    @JoinColumn(name = "employee")
    private PlaceEmployee employee;

    private boolean active;

    @Column(name = "order_date", columnDefinition = "DATETIME NULL")
    private LocalDateTime startTime;

    @Column(name = "finish_date", columnDefinition = "DATETIME NULL")
    private LocalDateTime finishTime;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"),
            name = "service_orders")
    private List<PlaceMenuOptionalService> services;

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

    public boolean isDone() {
        return active;
    }

    public void setActive(boolean isDone) {
        this.active = isDone;
    }

    public PlaceEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(PlaceEmployee employee) {
        this.employee = employee;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime orderDate) {
        this.startTime = orderDate;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOrders userOrders = (UserOrders) o;

        return id.equals(userOrders.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
