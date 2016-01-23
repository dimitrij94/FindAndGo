package com.example.pojo.dto;

/**
 * Created by Dmitrij on 21.01.2016.
 */
public class ScheduleTime {
    private int hour;
    private int minutes;
    private String part;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}