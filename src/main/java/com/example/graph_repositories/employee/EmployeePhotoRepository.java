package com.example.graph_repositories.employee;

import com.example.graph.photos.PlaceEmployeePhoto;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dmitrij on 25.02.2016.
 */
public interface EmployeePhotoRepository extends CrudRepository<PlaceEmployeePhoto, String> {
    PlaceEmployeePhoto findByWidth(int width);
}
