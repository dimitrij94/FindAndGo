package com.example.graph.schedules;

import com.example.graph.timeline.DayOfTheWeek;
import com.example.pojo.dto.ScheduleDTO;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class Schedule {
    @GraphId
    Long id;

    private int hourStartsAt;
    private int hourEndsAt;
    private int minuteStarsAt;
    private int minuteEndsAt;
    private boolean working;

    @RelatedTo(type = DayOfTheWeek.DAY_OF_THE_WEEK, direction = Direction.INCOMING)
    private DayOfTheWeek day;


    public Schedule() {
    }

    public Schedule(ScheduleDTO s) {
        this.hourEndsAt = s.getCloses().getHour();
        this.hourStartsAt = s.getOpen().getHour();
        this.minuteEndsAt = s.getCloses().getMinutes();
        this.minuteStarsAt = s.getOpen().getMinutes();
        this.working = s.isWorking();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHourStartsAt() {
        return hourStartsAt;
    }

    public void setHourStartsAt(int hourStartsAt) {
        this.hourStartsAt = hourStartsAt;
    }

    public int getHourEndsAt() {
        return hourEndsAt;
    }

    public void setHourEndsAt(int hourEndsAt) {
        this.hourEndsAt = hourEndsAt;
    }

    public int getMinuteStarsAt() {
        return minuteStarsAt;
    }

    public void setMinuteStarsAt(int minuteStarsAt) {
        this.minuteStarsAt = minuteStarsAt;
    }

    public int getMinuteEndsAt() {
        return minuteEndsAt;
    }

    public void setMinuteEndsAt(int minuteEndsAt) {
        this.minuteEndsAt = minuteEndsAt;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public DayOfTheWeek getDay() {
        return day;
    }

    public void setDay(DayOfTheWeek day) {
        this.day = day;
    }
}
