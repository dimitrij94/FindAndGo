package com.example.graph_repositories;

import com.example.graph.Person;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by Dmitrij on 29.02.2016.
 */
public interface PersonRepository extends GraphRepository<Person> {
    Person findByUserName(String userName);
}

