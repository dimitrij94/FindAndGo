package com.example.graph_repositories.menu;

import com.example.graph.photos.PlaceMenuServicePhoto;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 25.02.2016.
 */
public interface PlaceServicePhotoRepository extends CrudRepository<PlaceMenuServicePhoto,String> {

    PlaceMenuServicePhoto findByName(String photoName);
}
