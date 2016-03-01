package com.example.graph_repositories.place;

import com.example.graph.place.Place;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 21.02.2016.
 */

public interface PlaceRepository extends CrudRepository<Place, String> {

    Place findByName(String name);

    @Query("MATCH (n:Place{name:{name}})" +
            "RETURN COUNT(n)")
    long countPlacesWithName(String name);
}
