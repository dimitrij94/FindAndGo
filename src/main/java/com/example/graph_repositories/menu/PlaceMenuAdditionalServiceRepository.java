package com.example.graph_repositories.menu;

import com.example.graph.service.PlaceMenuAdditionalService;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 25.02.2016.
 */
public interface PlaceMenuAdditionalServiceRepository  extends CrudRepository<PlaceMenuAdditionalService,String> {
    PlaceMenuAdditionalService findByName(String name);
}
