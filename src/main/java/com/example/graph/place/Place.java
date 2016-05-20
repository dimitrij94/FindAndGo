package com.example.graph.place;

import com.example.graph.address.Address;
import com.example.graph.employee.PlaceEmployee;
import com.example.graph.owner.PlaceOwner;
import com.example.graph.photos.PlacePhoto;
import com.example.graph.schedules.Schedule;
import com.example.graph.service.PlaceMenuService;
import com.example.graph.user.PlaceUser;
import com.example.interfaces.PhotoContainable;
import com.example.pojo.dto.PlaceDTO;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class Place implements PhotoContainable {

    public final static String PHOTO = "PHOTO";
    public final static String HIRES = "HIRES";
    public final static String SPECIALIZES_AT = "SPECIALIZES_AT";
    public final static String PROVIDES = "PROVIDES";
    public final static String SCHEDULE = "SCHEDULE";
    public final static String ADDRESS = "ADDRESS";

    @GraphId
    Long id;

    @Indexed(unique = true)
    String name;

    String description;

    @RelatedTo(type = PlaceOwner.OWNS, direction = Direction.INCOMING)
    PlaceOwner owner;

    @RelatedTo(type = PHOTO, direction = Direction.OUTGOING)
    Set<PlacePhoto> photos;

    @RelatedTo(type = PlaceUser.MEMBER_OF, direction = Direction.INCOMING)
    Set<PlaceUser> member;

    @RelatedTo(type = HIRES, direction = Direction.OUTGOING)
    Set<PlaceEmployee> employees;

    @RelatedTo(type = SPECIALIZES_AT, direction = Direction.OUTGOING)
    PlaceSpecialization specialization;

    @RelatedTo(type = PROVIDES, direction = Direction.OUTGOING)
    Set<PlaceMenuService> services;

    @RelatedTo(type = SCHEDULE, direction = Direction.OUTGOING)
    Set<Schedule> placeSchedules;

    @RelatedTo(type = ADDRESS, direction = Direction.OUTGOING)
    Address address;

    public Place(PlaceDTO placeDTO) {
        this.name = placeDTO.getName();
        this.description = placeDTO.getDescription();

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

    public PlaceOwner getOwner() {
        return owner;
    }

    public void setOwner(PlaceOwner owner) {
        this.owner = owner;
    }

    public Set<PlacePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<PlacePhoto> photos) {
        this.photos = photos;
    }

    public Set<PlaceUser> getMember() {
        return member;
    }

    public void setMember(Set<PlaceUser> member) {
        this.member = member;
    }

    public Set<PlaceEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<PlaceEmployee> employees) {
        this.employees = employees;
    }

    public PlaceSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(PlaceSpecialization specialization) {
        this.specialization = specialization;
    }

    public Set<PlaceMenuService> getServices() {
        return services;
    }

    public void setServices(Set<PlaceMenuService> services) {
        this.services = services;
    }

    public Set<Schedule> getPlaceSchedules() {
        return placeSchedules;
    }

    public void setPlaceSchedules(Set<Schedule> placeSchedules) {
        this.placeSchedules = placeSchedules;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
