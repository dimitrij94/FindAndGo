package com.example.graph.schedules;

import com.example.graph.place.Place;
import com.example.pojo.dto.ScheduleDTO;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 28.02.2016.
 */
@NodeEntity
public class PlaceSchedule extends Schedule{


    @RelatedTo(type = "SCHEDULE", direction = Direction.INCOMING)
    private Place place;

    public PlaceSchedule(ScheduleDTO s) {
        super(s);
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
