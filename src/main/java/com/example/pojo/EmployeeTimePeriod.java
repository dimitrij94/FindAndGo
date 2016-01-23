package com.example.pojo;

import java.time.LocalTime;

/**
 * Created by Dmitrij on 29.11.2015.
 */
public class EmployeeTimePeriod {
    private LocalTime starts;
    private LocalTime ends;

    public EmployeeTimePeriod(LocalTime starts, LocalTime ends) {
        this.starts =starts;
        this.ends=ends;
    }

    public LocalTime getStarts() {
        return starts;
    }

    public void setStarts(LocalTime starts) {
        this.starts = starts;
    }

    public LocalTime getEnds() {
        return ends;
    }

    public void setEnds(LocalTime ends) {
        this.ends = ends;
    }
}
