package com.example.graph.service;

import com.example.graph.PlaceUserOrder;
import com.example.graph.employee.PlaceEmployee;
import com.example.graph.photos.PlaceMenuServicePhoto;
import com.example.graph.place.Place;
import com.example.interfaces.PhotoContainable;
import com.example.pojo.dto.MenuDTO;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * Created by Dmitrij on 20.02.2016.
 */
@NodeEntity
public class PlaceMenuService implements PhotoContainable {
    //OUTGOING RELATIONSHIPS NAMES
    public transient static final String PHOTO = "PHOTO";
    public transient static final String CAN_INCLUDE  = "CAN_INCLUDE";

    @GraphId
    Long id;

    @Indexed
    String name;

    String description;

    int price;

    int durationMinutes;

    @RelatedTo(type = PHOTO, direction = Direction.INCOMING)
    PlaceMenuServicePhoto photo;

    @RelatedTo(type = PlaceUserOrder.ORDERED, direction = Direction.INCOMING)
    Set<PlaceUserOrder> order;

    @RelatedTo(type = PlaceEmployee.CAN_PREFORM, direction = Direction.INCOMING)
    PlaceEmployee employee;

    @RelatedTo(type = Place.PROVIDES, direction = Direction.INCOMING)
    Place place;

    @RelatedTo(type = CAN_INCLUDE, direction = Direction.OUTGOING)
    Set<PlaceMenuAdditionalService> service;



    public PlaceMenuService(MenuDTO menuDTO) {
        this.name = menuDTO.getName();
        this.description = menuDTO.getDescription();
        this.price = menuDTO.getPrice();
        this.durationMinutes = menuDTO.getDurationMinutes();
    }

    public PlaceMenuService() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUserName() {
        return name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public PlaceMenuServicePhoto getPhoto() {
        return photo;
    }

    public void setPhoto(PlaceMenuServicePhoto photo) {
        this.photo = photo;
    }

    public Set<PlaceUserOrder> getOrder() {
        return order;
    }

    public void setOrder(Set<PlaceUserOrder> order) {
        this.order = order;
    }

    public PlaceEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(PlaceEmployee employee) {
        this.employee = employee;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Set<PlaceMenuAdditionalService> getService() {
        return service;
    }

    public void setService(Set<PlaceMenuAdditionalService> service) {
        this.service = service;
    }
}
