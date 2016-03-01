package com.example.graph.user;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.UUID;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class PlaceUserVerificationToken {
    @GraphId
    Long id;

    @Indexed
    String token;

    @RelatedTo(type = PlaceUser.VERIFICATION_TOKEN, direction = Direction.INCOMING)
    PlaceUser user;

    public PlaceUserVerificationToken() {
        this.token = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }
}
