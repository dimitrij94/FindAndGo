package com.example.domain.place;

import com.example.constants.WeekDays;
import com.example.pojo.dto.ScheduleDTO;
import com.example.pojo.dto.ScheduleTime;

import javax.persistence.*;
import java.time.LocalTime;

/**
 * Created by Dmitrij on 20.01.2016.
 */
@Entity
@Table(name = "place_schedule")
public class PlaceSchedule {
    @Id
    @GeneratedValue
    long id;

    @ManyToOne
    @JoinColumn(name = "place")
    Place place;

    @Enumerated(EnumType.STRING)
    WeekDays day;

    @Column(name = "is_working")
    boolean isWorking;

    LocalTime starts;

    LocalTime closes;

    public PlaceSchedule() {
    }

    public PlaceSchedule(ScheduleDTO schedule) {

        ScheduleTime open = schedule.getOpen();
        this.starts = LocalTime.of(open.getHour() + (open.getPart().equals("PM") ? 12 : 0),
                open.getMinutes());

        ScheduleTime closes = schedule.getCloses();
        this.closes = LocalTime.of(closes.getHour() + (closes.getPart().equals("PM") ? 12 : 0),
                closes.getMinutes());

        this.isWorking = schedule.isWorking();

        this.day = WeekDays.getInstace(schedule.getDayNum());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public WeekDays getDay() {
        return day;
    }

    public void setDay(WeekDays day) {
        this.day = day;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public LocalTime getStarts() {
        return starts;
    }

    public void setStarts(LocalTime starts) {
        this.starts = starts;
    }

    public LocalTime getCloses() {
        return closes;
    }

    public void setCloses(LocalTime closes) {
        this.closes = closes;
    }


}
