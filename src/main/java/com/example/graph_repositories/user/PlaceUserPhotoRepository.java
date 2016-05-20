package com.example.graph_repositories.user;

import com.example.graph.photos.PlaceUserPhoto;
import com.example.graph.user.PlaceUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 25.02.2016.
 */
public interface PlaceUserPhotoRepository extends CrudRepository<PlaceUserPhoto,String> {
    PlaceUserPhoto findByWidth(int width);

    PlaceUserPhoto findByName(String photoName);
    @Query("MATCH (:PlaceUser{userName:{userName}})-[:"+ PlaceUser.PHOTO+"]-(photo:PlaceUserPhoto{name:{photoName}})" +
            "RETURN photo")
    PlaceUserPhoto findByNameAndUserUserName(String photoName,String userName);

    @Query("MATCH(n) DETACH DELETE n")
    void clearDataBaseBeforeTests();
}
