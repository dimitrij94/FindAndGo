package com.example.graph.photos;

import com.example.graph.service.PlaceMenuService;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class PlaceMenuServicePhoto extends Photo{


    @RelatedTo(type = PlaceMenuService.PHOTO, direction = Direction.INCOMING)
    PlaceMenuService service;

    public PlaceMenuServicePhoto(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
    }
}
