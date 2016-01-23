package com.example.dao.photos;

import com.example.domain.employee.PlaceEmployeePhoto;
import com.example.domain.menu.PlaceMenuPhoto;
import com.example.domain.place.PlacePhoto;
import com.example.domain.users.PlaceUserPhoto;
import com.example.interfaces.PhotoCotainable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */
public interface PhotoDAO {
    @Transactional
    PlaceMenuPhoto addMenuPhoto(byte[] body, PhotoCotainable domain, String name);

    byte[] getMenuImageBodyByName(long id, String name);

    PlaceUserPhoto getUserPhotoByName(String name, long id);

    @Cacheable("photo-cache")
    byte[] getPlaceImageBodyByName(long id, String name);

    PlaceMenuPhoto getMenuPhotoByName(String name, long id);

    @Cacheable("photo-cache")
    PlacePhoto getPlacePhotoByName(String name, long id);

    @Transactional
    PlacePhoto addPlacePhoto(byte[] body, PhotoCotainable domain, String name);

    byte[] getUserPhotoBodyByName(String name, long id);

    PlaceEmployeePhoto getEmployeePhotoByName(String name, long id);

    @Transactional
    PlaceUserPhoto addPlaceUserPhoto(byte[] body, PhotoCotainable domain, String name);

    @Transactional
    PlaceEmployeePhoto addEmployeePhoto(byte[] body, PhotoCotainable domain, String name);
}
