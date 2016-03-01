package com.example.graph_repositories.user;

import com.example.graph.user.PlaceUserVerificationToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 27.02.2016.
 */
public interface PlaceUserVerificationTokenRepository  extends CrudRepository<PlaceUserVerificationToken,String> {
    
}
