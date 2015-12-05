package com.example.domain.users.employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Dmitrij on 27.11.2015.
 */
@Entity
@Table(name = "employees_pauses")
public class EmployeeVecations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name="starts",columnDefinition = "DATETIME NULL")
    private LocalTime start;
    @Column(name="ends",columnDefinition = "DATETIME NULL")
    private LocalTime ends;

    @Column(name="first_day",columnDefinition = "DATETIME NULL")
    private LocalDate firstDay;
    @Column(name="last_day",columnDefinition = "DATETIME NULL")
    private LocalDate lastDay;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    PlaceEmployee employee;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnds() {
        return ends;
    }

    public void setEnds(LocalTime ends) {
        this.ends = ends;
    }

    public LocalDate getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(LocalDate firstDay) {
        this.firstDay = firstDay;
    }

    public LocalDate getLastDay() {
        return lastDay;
    }

    public void setLastDay(LocalDate lastDay) {
        this.lastDay = lastDay;
    }

    public PlaceEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(PlaceEmployee employee) {
        this.employee = employee;
    }
}
