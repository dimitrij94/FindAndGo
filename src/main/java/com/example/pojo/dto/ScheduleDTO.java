package com.example.pojo.dto;

/**
 * Created by Dmitrij on 20.01.2016.
 */

public class ScheduleDTO {

    private ScheduleTime open;
    private ScheduleTime closes;
    private int dayNum;
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

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
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
