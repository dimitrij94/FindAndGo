package com.example.graph.timeline;

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
public class TimelineYear {
    public static final String MONTH = "MONTH";

    @GraphId
    Long id;
    @Indexed
    int year;
    @RelatedTo(type = MONTH, direction = Direction.OUTGOING)
    Set<TimelineMonth> month;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<TimelineMonth> getMonth() {
        return month;
    }

    public void setMonth(Set<TimelineMonth> month) {
        this.month = month;
    }
}
