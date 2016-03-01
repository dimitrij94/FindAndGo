package com.example.graph_repositories;

import com.example.graph.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 29.02.2016.
 */
public interface PersonRepository extends CrudRepository<Person,String> {
    Person findByUserName(String userName);
}

