package com.example.graph.timeline;

import com.example.graph.schedules.Schedule;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class DayOfTheWeek {
    public static final String DAY_OF_THE_WEEK = "DAY_OF_THE_WEEK";

    @GraphId
    Long id;

    @Indexed
    String dayName;

    @RelatedTo(type = DAY_OF_THE_WEEK, direction = Direction.OUTGOING)
    Set<Schedule> placeSchedules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Set<Schedule> getPlaceSchedules() {
        return placeSchedules;
    }

    public void setPlaceSchedules(Set<Schedule> placeSchedules) {
        this.placeSchedules = placeSchedules;
    }
}
