package com.example.graph.service;

import com.example.graph.PlaceUserOrder;
import com.example.pojo.dto.ServiceDTO;
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
public class PlaceMenuAdditionalService {
    @GraphId
    Long id;

    @Indexed
    String name;
    String description;
    int additionalPrice;
    int duration;

    @RelatedTo(type = PlaceMenuService.CAN_INCLUDE, direction = Direction.INCOMING)
    PlaceMenuService service;

    @RelatedTo(type = PlaceUserOrder.INCLUDES, direction = Direction.INCOMING)
    Set<PlaceUserOrder> orders;


    public PlaceMenuAdditionalService() {
    }

    public PlaceMenuAdditionalService(ServiceDTO serviceDTO) {
        this.name = serviceDTO.getName();
        this.description = serviceDTO.getServiceDescription();
        this.additionalPrice = serviceDTO.getServicePrice();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(int additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public PlaceMenuService getService() {
        return service;
    }

    public void setService(PlaceMenuService service) {
        this.service = service;
    }

    public Set<PlaceUserOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<PlaceUserOrder> orders) {
        this.orders = orders;
    }
}
