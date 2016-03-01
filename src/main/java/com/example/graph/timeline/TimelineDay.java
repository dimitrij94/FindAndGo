package com.example.graph.timeline;

import com.example.graph.PlaceUserOrder;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class TimelineDay {

    @GraphId
    Long id;
    int day;

    @RelatedTo(type = DayOfTheWeek.DAY_OF_THE_WEEK, direction = Direction.OUTGOING)
    DayOfTheWeek dayOfTheWeek;

    @RelatedTo(type = TimelineMonth.DAY, direction = Direction.INCOMING)
    TimelineMonth month;

    @RelatedTo(type = PlaceUserOrder.ORDERED_AT, direction = Direction.INCOMING)
    Set<PlaceUserOrder> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public TimelineMonth getMonth() {
        return month;
    }

    public void setMonth(TimelineMonth month) {
        this.month = month;
    }

    public Set<PlaceUserOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<PlaceUserOrder> orders) {
        this.orders = orders;
    }
}
