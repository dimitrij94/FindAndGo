package com.example.neo_services.image;

import com.example.constants.image.ImageContainerType;
import com.example.constants.image.sizes.ImageSize;
import com.example.constants.image.sizes.MenuImageSizes;
import com.example.graph.photos.PlaceMenuServicePhoto;
import com.example.graph.service.PlaceMenuService;
import com.example.graph_repositories.menu.PlaceServicePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 19.05.2016.
 */
@Service
public class ServiceImageService extends GenericImageService<PlaceMenuServicePhoto, PlaceMenuService> {

    @Autowired
    PlaceServicePhotoRepository repository;

    @Override
    protected CrudRepository<PlaceMenuServicePhoto, String> getRepository() {
        return repository;
    }

    @Override
    protected PlaceMenuServicePhoto getPhoto(int width, int height, ImageSize size, PlaceMenuService domain) {
        return new PlaceMenuServicePhoto(width, height, size.getName(), domain);
    }

    @Override
    protected ImageSize[] getPhotoSizes() {
        return MenuImageSizes.values();
    }

    @Override
    protected ImageContainerType getImageContainerType() {
        return ImageContainerType.PLACE_MENU;
    }

    @Override
    public PlaceMenuServicePhoto getPhoto(String photoName) {
        return repository.findByName(photoName);
    }
}
