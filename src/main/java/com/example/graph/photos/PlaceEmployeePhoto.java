package com.example.graph.photos;

import com.example.graph.employee.PlaceEmployee;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Created by Dmitrij on 21.02.2016.
 */
@NodeEntity
public class PlaceEmployeePhoto extends Photo{

    @RelatedTo(type = "PHOTO", direction = Direction.INCOMING)
    PlaceEmployee employee;

    public PlaceEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(PlaceEmployee employee) {
        this.employee = employee;
    }
}
