package com.example.graph.photos;

import com.example.graph.place.Place;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class PlacePhoto extends Photo {
    @RelatedTo(type = Place.PHOTO, direction = Direction.INCOMING)
    Place place;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public PlacePhoto place(Place place) {
        this.place = place;
        return this;
    }

    public PlacePhoto(int width, int height, String name, Place place) {
        super.width = width;
        super.heigth = height;
        super.name = name;
        this.place = place;
    }

    public PlacePhoto() {
    }
}
