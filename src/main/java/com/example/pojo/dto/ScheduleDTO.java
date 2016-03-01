package com.example.pojo.dto;

/**
 * Created by Dmitrij on 20.01.2016.
 */

public class ScheduleDTO {

    private ScheduleTime open;
    private ScheduleTime closes;
    private int dayOfTheWeekNum;
    private String name;
    private boolean working;


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

    public int getDayOfTheWeekNum() {
        return dayOfTheWeekNum;
    }

    public void setDayOfTheWeekNum(int dayOfTheWeekNum) {
        this.dayOfTheWeekNum = dayOfTheWeekNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
}
