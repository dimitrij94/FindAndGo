package com.example.neo_services.image;

import com.example.constants.image.ImageContainerType;
import com.example.constants.image.sizes.ImageSize;
import com.example.constants.image.sizes.UserImageSizes;
import com.example.graph.photos.PlaceUserPhoto;
import com.example.graph.user.PlaceUser;
import com.example.graph_repositories.user.PlaceUserPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Dmitrij on 19.05.2016.
 */
@Service
public class UserPhotoService extends GenericImageService<PlaceUserPhoto,PlaceUser>{

    @Autowired
    PlaceUserPhotoRepository repository;

    @Override
    protected CrudRepository<PlaceUserPhoto,String> getRepository() {
        return repository;
    }

    @Override
    protected PlaceUserPhoto getPhoto(int width, int height, ImageSize size, PlaceUser domain) {
        return new PlaceUserPhoto(width,height,size.getName()).user(domain);
    }

    @Override
    protected ImageSize[] getPhotoSizes() {
        return UserImageSizes.values();
    }

    @Override
    protected ImageContainerType getImageContainerType() {
        return ImageContainerType.PLACE_USER;
    }

    @Override
    public PlaceUserPhoto getPhoto(String photoName) {
        return repository.findByName(photoName);
    }
}
