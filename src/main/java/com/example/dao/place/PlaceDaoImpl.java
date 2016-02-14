package com.example.dao.place;

import com.example.dao.DBBean;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.place.Place;
import com.example.domain.place.PlaceAddress;
import com.example.domain.place.PlaceSchedule;
import com.example.domain.users.PlaceUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitrij on 21.01.2016.
 */
@Repository
public class PlaceDaoImpl extends DBBean implements PlaceDAO {

    @Override
    public Place getPlaceById(long id) {
        return this.em.find(Place.class, id);
    }

    @Override
    public Place getOwnerPlaceById(long id, PlaceOwner owner) {
        return this.em.createQuery("SELECT e FROM PlaceOwner e WHERE e.id=:id", Place.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void addNewPlace(Place place, PlaceAddress placeAddress, PlaceOwner owner, List<PlaceSchedule> schedules) {
        place.setPlaceOwner(owner);
        this.em.persist(place);

        for (int i = 0; i < schedules.size(); i++) {
            PlaceSchedule s = schedules.get(i);
            s.setPlace(place);
            em.persist(s);
            schedules.set(i, s);
        }

        place.setPlaceSchedules(schedules);

        placeAddress.setPlace(place);
        this.em.persist(placeAddress);

        place.setAddress(placeAddress);
        this.em.merge(place);

        owner.getPlaces().add(place);
        this.em.merge(owner);
    }

    @Override
    public List<Place> getMainPlaces() {
        List<Place> mainPlaces =
                this.em.createQuery("SELECT e FROM Place e ORDER BY e.placeFollowersNum DESC, e.placeFinalRating DESC", Place.class)
                        .getResultList();
        if (mainPlaces.size() > 15) {
            mainPlaces.subList(0, 20);
        }
        return mainPlaces;
    }



    @Override
    @Transactional
    public void newPlaceLike(PlaceUser user, Place place) {
        user.setUserPlaces(getUserPlacesAsList(place, user.getUserPlaces()));
        place.setPlaceUsers(setAsList(place.getPlaceUsers(), user));
        place.setPlaceFollowersNum(place.getPlaceFollowersNum() + 1);
        em.flush();
    }



    @Override
    @Transactional
    public void removePlaceLike(PlaceUser user, long placeId) {
        Place place = getPlaceById(placeId);
        user.getUserPlaces().remove(place);
        place.getPlaceUsers().remove(user);
        place.setPlaceFollowersNum(place.getPlaceFollowersNum() - 1);
        em.flush();
    }

    @Override
    public boolean isUserUsedPlace(Place p, PlaceUser user) {
        return ((long) em.createQuery("SELECT COUNT (e.id) FROM UserOrders e " +
                "WHERE e.user.id=:uId AND e.place.id=:pId")
                .setParameter("uId", user.getId())
                .setParameter("pId", p.getId())
                .getSingleResult()) > 0;
    }



    @Override
    public int getPlaceFinalRating(Place place) {
        return (int) Math.round((double) ((em.createQuery("SELECT AVG (m.menuFinalRating) FROM Place e, e.placeMenu m WHERE e.id=:pId")
                .setParameter("pId", place.getId())
                .getSingleResult())));
    }

    @Override
    @Transactional
    public void updatePlaceRating(Place place, int finalRating) {
        place.setPlaceFinalRating(finalRating);
        em.merge(place);
    }

    @Override
    public long isUserLikedPlace(PlaceUser user, Place p) {
        return (long) em.createQuery("SELECT COUNT (e) FROM Place e INNER JOIN e.placeUsers u WHERE u.id=:uId AND e.id=:pId")
                .setParameter("uId", user.getId())
                .setParameter("pId", p.getId())
                .getSingleResult();
    }

    @Override
    public long countPlacesWithName(String name) {
        return (long) em.createQuery("SELECT COUNT (e) FROM Place e WHERE e.placeName=:name")
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    @Transactional
    public Place addNewPlace(Place place, PlaceOwner owner) {
        place.setPlaceOwner(owner);
        em.persist(place);
        owner.setPlaces(setAsList(owner.getPlaces(), place));
        em.merge(owner);
        em.flush();
        return place;
    }

}
