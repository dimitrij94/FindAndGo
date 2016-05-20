package com.example.graph.schedules;

import com.example.graph.employee.PlaceEmployee;
import com.example.pojo.dto.ScheduleDTO;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 28.02.2016.
 */
public class EmployeeSchedule extends Schedule {

    @RelatedTo(type = PlaceEmployee.SCHEDULE, direction = Direction.INCOMING)
    private PlaceEmployee placeEmployee;

    public EmployeeSchedule(ScheduleDTO dto) {
        super(dto);
    }

    public PlaceEmployee getPlaceEmployee() {
        return placeEmployee;
    }

    public void setPlaceEmployee(PlaceEmployee placeEmployee) {
        this.placeEmployee = placeEmployee;
    }
}
