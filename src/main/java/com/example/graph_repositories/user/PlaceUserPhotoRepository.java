package com.example.graph_repositories.user;

import com.example.graph.photos.PlaceUserPhoto;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 25.02.2016.
 */
public interface PlaceUserPhotoRepository extends CrudRepository<PlaceUserPhoto,String> {
    PlaceUserPhoto findByWidth(int width);
}
