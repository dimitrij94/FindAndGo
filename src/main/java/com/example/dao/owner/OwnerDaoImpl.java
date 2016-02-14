package com.example.dao.owner;

import com.example.dao.DBBean;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */
@Repository
public class OwnerDaoImpl extends DBBean implements OwnerDAO{

    @Override
    public PlaceOwner getOwnerById(Long id) {
        return em.find(PlaceOwner.class, id);
    }

    @Override
    public Place getOwnerPlace(long placeId, PlaceOwner user) {
        return (Place) em.createQuery("SELECT e FROM Place e WHERE e.id=:id AND e.placeOwner.id=:uId")
                .setParameter("id", placeId)
                .setParameter("uId", user.getId())
                .getSingleResult();
    }

    @Override
    @Transactional
    public Place addNewPlace(Place place, PlaceOwner owner) {
        place.setPlaceOwner(owner);
        em.persist(place);
        owner.setPlaces(setAsList(owner.getPlaces(), place));
        em.merge(owner);
        return place;
    }

    @Override
    public long countOwnersWithName(String name) {
        return (long) em.createQuery("SELECT count (e) FROM PlaceOwner e WHERE e.name=:name").setParameter("name",name).getSingleResult();
    }

    @Override
    public long countOwnersWithEmail(String email) {
        return (long) em.createQuery("SELECT count (e) FROM PlaceOwner e WHERE e.email=:email").setParameter("email",email).getSingleResult();
    }

    @Override
    @Transactional
    public PlaceOwner addNewOwner(PlaceOwner owner) {
        em.persist(owner);
        em.flush();
        em.merge(owner);
        return owner;
    }

}
