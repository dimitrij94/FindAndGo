package com.example.graph.owner;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.UUID;

/**
 * Created by Dmitrij on 27.02.2016.
 */
@NodeEntity
public class PlaceOwnerVerificationToken {
    @GraphId
    Long id;
    @Indexed
    String token;
    @RelatedTo(type = PlaceOwner.VERIFICATION_TOKEN, direction = Direction.INCOMING)
    PlaceOwner placeOwner;

    public PlaceOwnerVerificationToken() {
        this.token = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PlaceOwner getPlaceOwner() {
        return placeOwner;
    }

    public void setPlaceOwner(PlaceOwner placeOwner) {
        this.placeOwner = placeOwner;
    }
}
