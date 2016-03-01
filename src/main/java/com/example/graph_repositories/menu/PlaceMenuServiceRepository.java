package com.example.graph_repositories.menu;

import com.example.graph.service.PlaceMenuService;
import org.springframework.data.repository.CrudRepository;

public interface PlaceMenuServiceRepository extends CrudRepository<PlaceMenuService, String> {
    PlaceMenuService findByName(String name);

}
