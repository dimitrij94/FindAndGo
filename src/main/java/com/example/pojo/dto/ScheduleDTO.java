package com.example.pojo.dto;

/**
 * Created by Dmitrij on 20.01.2016.
 */
public class ScheduleDTO {

    private ScheduleTime open;
    private ScheduleTime closes;
    private String day;
    private boolean isWorking;


    public ScheduleTime getOpen() {
        return open;
    }

    public void setOpen(ScheduleTime open) {
        this.open = open;
    }

    public ScheduleTime getCloses() {
        return closes;
    }

    public void setCloses(ScheduleTime closes) {
        this.closes = closes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }
}
