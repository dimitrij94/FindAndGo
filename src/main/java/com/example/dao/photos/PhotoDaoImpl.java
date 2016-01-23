package com.example.dao.photos;

import com.example.dao.DBBean;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.employee.PlaceEmployeePhoto;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuPhoto;
import com.example.domain.place.Place;
import com.example.domain.place.PlacePhoto;
import com.example.domain.users.PlaceUser;
import com.example.domain.users.PlaceUserPhoto;
import com.example.interfaces.PhotoCotainable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */

@Repository
public class PhotoDaoImpl extends DBBean implements PhotoDAO {

    @Transactional
    @Override
    public PlaceMenuPhoto addMenuPhoto(byte[] body, PhotoCotainable domain, String name) {
        PlaceMenu menu = (PlaceMenu) domain;
        PlaceMenuPhoto photo = new PlaceMenuPhoto(body, name, menu);
        this.em.persist(photo);
        menu.setPhoto(setAsList(menu.getPhoto(), photo));
        em.merge(menu);
        return photo;
    }
    @Override
    public byte[] getMenuImageBodyByName(long id, String name) {
        return (this.em.createQuery("SELECT e FROM PlaceMenuPhoto e WHERE e.menu.id=:id AND e.name=:name", PlaceMenuPhoto.class)
                .setParameter("id", id)
                .setParameter("name", name)
                .getSingleResult())
                .getBody();
    }
    @Override
    @Transactional
    public PlaceEmployeePhoto addEmployeePhoto(byte[] body, PhotoCotainable domain, String name) {
        PlaceEmployee employee = (PlaceEmployee) domain;
        PlaceEmployeePhoto photo = new PlaceEmployeePhoto();
        photo.setName(name);
        photo.setEmployee(employee);
        photo.setBody(body);
        em.persist(photo);
        employee.setPhoto(setAsList(employee.getPhoto(), photo));
        em.merge(employee);
        return photo;
    }

    @Override
    public PlaceUserPhoto getUserPhotoByName(String name, long id) {
        return em.createQuery("SELECT e FROM PlaceUserPhoto e WHERE e.user.id=:userId AND e.name=:name", PlaceUserPhoto.class)
                .setParameter("userId", id)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    @Cacheable("photo-cache")
    public byte[] getPlaceImageBodyByName(long id, String name) {
        return (this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id", PlacePhoto.class)
                .setParameter("name", name)
                .setParameter("id", id).getSingleResult())
                .getBody();
    }


    @Override
    public PlaceMenuPhoto getMenuPhotoByName(String name, long id) {
        return this.em.createQuery("SELECT e FROM PlaceMenuPhoto e WHERE e.menu.id=:id AND e.name=:name", PlaceMenuPhoto.class)
                .setParameter("id", id)
                .setParameter("name", name)
                .getSingleResult();
    }


    @Cacheable("photo-cache")
    @Override
    public PlacePhoto getPlacePhotoByName(String name, long id) {
        return this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id", PlacePhoto.class)
                .setParameter("name", name)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    @Transactional
    public PlacePhoto addPlacePhoto(byte[] body, PhotoCotainable domain, String name) {
        Place place = (Place) domain;
        PlacePhoto photo = new PlacePhoto(body, name);
        photo.setPlace(place);
        this.em.persist(photo);
        place.setPlacePhotos(savePlacePhotoAsList(place.getPlacePhotos(), photo));
        em.merge(place);
        return photo;
    }

    @Override
    public byte[] getUserPhotoBodyByName(String name, long id) {
        return em.createQuery("SELECT e FROM PlaceUserPhoto e WHERE e.user.id=:userId AND e.name=:name", PlaceUserPhoto.class)
                .setParameter("userId", id)
                .setParameter("name", name)
                .getSingleResult()
                .getBody();
    }


    @Override
    public PlaceEmployeePhoto getEmployeePhotoByName(String name, long id) {
        return em.createQuery("SELECT e FROM PlaceEmployeePhoto e WHERE e.name = :name AND e.id = :id", PlaceEmployeePhoto.class)
                .setParameter("name", name)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public PlaceUserPhoto addPlaceUserPhoto(byte[] body, PhotoCotainable domain, String name) {
        PlaceUser user = (PlaceUser) domain;
        PlaceUserPhoto photo = new PlaceUserPhoto(body, user, name);
        em.persist(photo);
        setUserPhotosAsList(user.getPhotos(), photo);
        em.merge(user);
        return photo;
    }

}
