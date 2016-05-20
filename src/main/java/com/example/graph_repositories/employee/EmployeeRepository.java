package com.example.graph_repositories.employee;

import com.example.graph.employee.PlaceEmployee;
import com.example.graph.place.Place;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 23.02.2016.
 */
public interface EmployeeRepository extends CrudRepository<PlaceEmployee, String> {
    //Create ,@QueryResult annotated, class, later
    @Query("MATCH (place:Place{name:{placeName}})-[:"+ Place.HIRES+"]->(e:PlaceEmployee{employeeName})" +
            "RETURN e")
    PlaceEmployee findByNameAndPlaceName(String placeName, String employeeName);
}
