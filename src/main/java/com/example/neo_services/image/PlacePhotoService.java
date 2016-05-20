package com.example.neo_services.image;

import com.example.constants.image.ImageContainerType;
import com.example.constants.image.sizes.ImageSize;
import com.example.constants.image.sizes.PlaceImageSizes;
import com.example.graph.photos.PlacePhoto;
import com.example.graph.place.Place;
import com.example.graph_repositories.place.PlacePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 19.05.2016.
 */
@Service
public class PlacePhotoService extends GenericImageService<PlacePhoto, Place> {

    @Autowired
    @Qualifier("placePhotoRepository")
    PlacePhotoRepository repository;

    @Override
    protected CrudRepository<PlacePhoto, String> getRepository() {
        return repository;
    }

    @Override
    protected PlacePhoto getPhoto(int width, int height, ImageSize size, Place domain) {
        return new PlacePhoto(width, height, size.getName(), domain);
    }

    @Override
    protected ImageSize[] getPhotoSizes() {
        return PlaceImageSizes.values();
    }

    @Override
    protected ImageContainerType getImageContainerType() {
        return ImageContainerType.PLACE;
    }

    @Override
    public PlacePhoto getPhoto(String photoName) {
        return repository.findByName(photoName);
    }
}

