package com.example.dao.place;

import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import com.example.domain.place.PlaceAddress;
import com.example.domain.place.PlaceSchedule;
import com.example.domain.users.PlaceUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitrij on 21.01.2016.
 */
public interface PlaceDAO {
    Place getPlaceById(long id);

    Place getOwnerPlaceById(long id, PlaceOwner owner);

    @Transactional
    void addNewPlace(Place place, PlaceAddress placeAddress, PlaceOwner owner, List<PlaceSchedule> schedules);

    List<Place> getMainPlaces();



    @Transactional
    void newPlaceLike(PlaceUser user, Place place);

    @Transactional
    void removePlaceLike(PlaceUser user, long placeId);

    boolean isUserUsedPlace(Place p, PlaceUser user);

    int getPlaceFinalRating(Place place);

    @Transactional
    void updatePlaceRating(Place place, int finalRating);

    long isUserLikedPlace(PlaceUser user, Place p);

    long countPlacesWithName(String name);

    Place addNewPlace(Place place, PlaceOwner ownerById);
}
