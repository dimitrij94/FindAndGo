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
public class TimelineMonth {
    public static final String DAY = "DAY";

    @GraphId
    Long id;

    @Indexed
    int monthNum;

    String name;

    @RelatedTo(type = TimelineYear.MONTH, direction = Direction.INCOMING)
    Set<TimelineYear> year;

    @RelatedTo(type = DAY, direction = Direction.OUTGOING)
    Set<TimelineDay> days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(int monthNum) {
        this.monthNum = monthNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TimelineYear> getYear() {
        return year;
    }

    public void setYear(Set<TimelineYear> year) {
        this.year = year;
    }

    public Set<TimelineDay> getDays() {
        return days;
    }

    public void setDays(Set<TimelineDay> days) {
        this.days = days;
    }
}
