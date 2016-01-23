package com.example.pojo.dto;

/**
 * Created by Dmitrij on 02.11.2015.
 */
public class PlaceDTO {
    private String name;
    private String description;
    private String specialization;
    private ScheduleDTO[] schedules;

    public PlaceDTO() {
        schedules = new ScheduleDTO[7];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public ScheduleDTO[] getSchedules() {
        return schedules;
    }

    public void setSchedules(ScheduleDTO[] schedules) {
        this.schedules = schedules;
    }
}