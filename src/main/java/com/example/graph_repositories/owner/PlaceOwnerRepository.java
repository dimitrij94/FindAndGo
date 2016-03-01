package com.example.graph_repositories.owner;

import com.example.graph.owner.PlaceOwner;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 24.02.2016.
 */

public interface PlaceOwnerRepository extends CrudRepository<PlaceOwner,String>{
    PlaceOwner findByUserName(String userName);

    PlaceOwner findByEmail(String email);
}
