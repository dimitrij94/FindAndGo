package com.example.dao.owner;

import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */
public interface OwnerDAO {
    PlaceOwner getOwnerById(Long id);

    Place getOwnerPlace(long placeId, PlaceOwner user);

    @Transactional
    Place addNewPlace(Place place, PlaceOwner owner);

    long countOwnersWithName(String name);

    long countOwnersWithEmail(String email);

    PlaceOwner addNewOwner(PlaceOwner owner);
}
