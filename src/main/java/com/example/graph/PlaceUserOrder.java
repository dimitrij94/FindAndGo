package com.example.graph;

import com.example.graph.comments.OrderComment;
import com.example.graph.employee.PlaceEmployee;
import com.example.graph.service.PlaceMenuAdditionalService;
import com.example.graph.service.PlaceMenuService;
import com.example.graph.timeline.TimelineDay;
import com.example.graph.user.PlaceUser;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class PlaceUserOrder {
    //OUTGOING RELATIONSHIPS NAMES
    public transient static final String ORDERED = "ORDERED";
    public transient static final String INCLUDES = "INCLUDES";
    public transient static final String PREVIOUS = "PREVIOUS";
    public transient static final String ORDERED_AT = "ORDERED_AT";


    @GraphId
    Long id;
    long time;

    @RelatedTo(type = "PREFORMS", direction = Direction.INCOMING)
    PlaceEmployee employee;

    @RelatedTo(type = ORDERED, direction = Direction.OUTGOING)
    PlaceMenuService service;

    @RelatedTo(type = INCLUDES, direction = Direction.OUTGOING)
    Set<PlaceMenuAdditionalService> placeMenuAdditionalServices;

    @RelatedTo(type = PREVIOUS, direction = Direction.OUTGOING)
    PlaceUserOrder order;

    @RelatedTo(type = "ORDERS", direction = Direction.INCOMING)
    PlaceUser user;

    @RelatedTo(type = "ORDERED_AT", direction = Direction.OUTGOING)
    TimelineDay day;

    @RelatedTo(type = "WAS_RATED", direction = Direction.INCOMING)
    Set<OrderComment> comments;

    public PlaceUserOrder addComment(OrderComment comment) {
        if (this.comments == null) this.comments = new HashSet<>();
        this.comments.add(comment);
        return this;
    }

    public PlaceUserOrder addAdditionalServices(PlaceMenuAdditionalService service) {
        if (this.placeMenuAdditionalServices == null) this.placeMenuAdditionalServices = new HashSet<>();
        this.placeMenuAdditionalServices.add(service);
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public PlaceEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(PlaceEmployee employee) {
        this.employee = employee;
    }

    public PlaceMenuService getService() {
        return service;
    }

    public void setService(PlaceMenuService service) {
        this.service = service;
    }

    public Set<PlaceMenuAdditionalService> getPlaceMenuAdditionalServices() {
        return placeMenuAdditionalServices;
    }

    public void setPlaceMenuAdditionalServices(Set<PlaceMenuAdditionalService> placeMenuAdditionalServices) {
        this.placeMenuAdditionalServices = placeMenuAdditionalServices;
    }

    public PlaceUserOrder getOrder() {
        return order;
    }

    public void setOrder(PlaceUserOrder order) {
        this.order = order;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }

    public TimelineDay getDay() {
        return day;
    }

    public void setDay(TimelineDay day) {
        this.day = day;
    }

    public Set<OrderComment> getComments() {
        return comments;
    }

    public void setComments(Set<OrderComment> comments) {
        this.comments = comments;
    }
}
