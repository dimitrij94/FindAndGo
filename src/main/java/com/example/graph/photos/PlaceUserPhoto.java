package com.example.graph.photos;

import com.example.graph.user.PlaceUser;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class PlaceUserPhoto extends Photo{

    @RelatedTo(type = "PHOTO", direction = Direction.INCOMING)
    PlaceUser user;

    public PlaceUserPhoto(int width, int height) {
        this.width = width;
        this.heigth = height;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }
}
