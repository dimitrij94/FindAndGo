//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.dao;

import com.example.dao.IDBBean;
import com.example.domain.Order;
import com.example.domain.Place;
import com.example.domain.PlaceEvent;
import com.example.domain.PlaceUser;
import com.example.domain.addresses.PlaceAddress;
import com.example.domain.addresses.UserAddress;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.domain.photos.PlacePhoto;
import com.example.domain.ratings.PlaceMenuRating;
import com.example.domain.ratings.PlaceRating;
import com.example.domain.registration.Authorities;
import com.example.domain.registration.VerificationToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DBBean implements IDBBean {
    @PersistenceContext
    EntityManager em;

    public DBBean() {
    }

    public PlacePhoto getUserPhoto() {
        Query query = this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.id=:id");
        return (PlacePhoto) query.getSingleResult();
    }

    public Place getPlaceById(long id) {
        return (Place) this.em.find(Place.class, Long.valueOf(id));
    }

    public Place getOwnerPlaceById(long id, PlaceUser user) {
        return (Place) this.em.createQuery("SELECT e FROM PlaceUser.ownerPlaces e WHERE e.id=:id").setParameter("id", Long.valueOf(id)).getSingleResult();
    }

    public PlaceMenu getMenuById(long id) {
        return (PlaceMenu) this.em.find(PlaceMenu.class, Long.valueOf(id));
    }

    public PlaceUser authorization(String email, String pass) {
        Query userQuery = this.em.createQuery("SELECT e FROM PlaceUser e WHERE (e.userEmail=:email OR e.userName=:email) AND e.userPass=:pass").setParameter("email", email).setParameter("pass", pass);
        PlaceUser user = null;
        return (user = (PlaceUser) userQuery.getSingleResult()) != null ? user : null;
    }

    @Transactional
    public void addNewPlace(Place place, PlaceAddress placeAddress, PlaceUser owner) {
        place.setPlaceOwner(owner);
        this.em.persist(place);
        this.em.flush();
        placeAddress.setPlace(place);
        this.em.persist(placeAddress);
        place.setAddress(placeAddress);
        this.em.flush();
        owner.getOwnerPlaces().add(place);
        Authorities authorities = new Authorities(Long.toString(place.getId().longValue()));
        authorities.setUser(owner);
        this.em.persist(authorities);
        this.em.flush();
        owner.getAuthorities().add(authorities);
    }

    @Cacheable({"maincahe"})
    public ArrayList<PlaceEvent> getMainEvents() {
        ArrayList<PlaceEvent> mainEvents = (ArrayList<PlaceEvent>) this.em.createQuery("SELECT e FROM PlaceEvent  e order by e.users.size").getResultList();
        if (mainEvents.size() > 5) {
            mainEvents.subList(0, 4);
        }

        Comparator<PlaceEvent> comparator = (e1, e2) -> {
            return Integer.valueOf(e1.getUsers().size()).compareTo(e2.getUsers().size());
        };
        mainEvents.sort(comparator);
        return mainEvents;
    }

    @Cacheable({"maincahe"})
    public List<Place> getMainPlaces() {
        List<Place> mainPlaces = this.em.createQuery("SELECT e FROM Place e").getResultList();
        if (mainPlaces.size() > 15) {
            mainPlaces.subList(0, 15);
        }

        Comparator<Place> comparator = (Place p1, Place p2) -> {
            int r = p1.getPlaceFinalRating() - p2.getPlaceFinalRating();
            int u = p1.getPlaceUsers().size() - p2.getPlaceUsers().size();
            return u != 0 ? (u < 0 ? -1 : 1) : (r != 0 ? (r < 0 ? -1 : 1) : 0);
        };
        mainPlaces.sort(comparator);
        return mainPlaces;
    }

    public Integer changePlaceMenuRating(long placeMenuId, long userId, int rating) {
        byte finalRating = 0;

        try {
            this.em.getTransaction().begin();
            Query ex = this.em.createQuery("SELECT u FROM PlaceMenuRating u WHERE u.user.id=:userId AND u.menu.id=:menuId");
            if (ex.getSingleResult() == null) {
                this.em.persist((new PlaceMenuRating()).rating((float) rating).menu((PlaceMenu) this.em.find(PlaceMenu.class, Long.valueOf(placeMenuId))).user((PlaceUser) this.em.find(PlaceUser.class, Long.valueOf(userId))));
                this.em.getTransaction().commit();
            } else {
                this.em.createQuery("update PlaceMenuRating m set m.rating=:rating WHERE m.menu.id=:menuRating AND m.user.id=:userId").setParameter("rating", Integer.valueOf(rating)).setParameter("menuRating", Long.valueOf(placeMenuId)).setParameter("userId", Long.valueOf(userId)).executeUpdate();
            }

            int menuRatingsCount = this.em.createQuery("SELECT COUNT (r.id) FROM PlaceMenuRating r WHERE r.menu.id=:menuId").setParameter("menuId", Long.valueOf(placeMenuId)).getMaxResults();
            long menuRatingSumm = ((Long) this.em.createQuery("SELECT SUM (r.rating) FROM PlaceMenuRating r WHERE r.menu.id=:menuId").setParameter("menuId", Long.valueOf(placeMenuId)).getSingleResult()).longValue();
            if (menuRatingsCount != 0) {
                int finalRating1 = Math.round((float) (menuRatingSumm / (long) menuRatingsCount));
                this.em.getTransaction().begin();
                this.em.createQuery("UPDATE PlaceMenu m SET m.menuFinalRating=:menuRating WHERE m.id=:menuId").setParameter("menuId", Long.valueOf(placeMenuId)).setParameter("menuRating", Integer.valueOf(finalRating1)).executeUpdate();
                this.em.getTransaction().commit();
                return Integer.valueOf(finalRating1);
            } else {
                throw new ArithmeticException("divade on zero when calc final rating");
            }
        } catch (Exception var11) {
            this.em.getTransaction().rollback();
            var11.printStackTrace();
            return Integer.valueOf(finalRating);
        }
    }

    public Integer changePlaceRating(long placeId, long userId, int rating) {
        Query chageRatingQuery = this.em.createQuery("SELECT r FROM PlaceRating r WHERE r.user.id=:userId AND r.place.id=:placeId").setParameter("userId", Long.valueOf(userId)).setParameter("placeId", Long.valueOf(placeId));
        if (chageRatingQuery.getSingleResult() != null) {
            try {
                this.em.getTransaction().begin();
                this.em.createQuery("UPDATE PlaceRating r SET r.rating=:rating WHERE r.user.id=:userId AND r.place.id=:placeId").setParameter("rating", Integer.valueOf(rating)).setParameter("userId", Long.valueOf(userId)).setParameter("placeId", Long.valueOf(placeId)).executeUpdate();
                this.em.getTransaction().commit();
            } catch (Exception var15) {
                this.em.getTransaction().rollback();
                var15.printStackTrace();
            }
        } else {
            try {
                this.em.getTransaction().begin();
                this.em.persist((new PlaceRating()).place((Place) this.em.find(Place.class, Long.valueOf(placeId))).user((PlaceUser) this.em.find(PlaceUser.class, Long.valueOf(userId))).rating(rating));
                this.em.getTransaction().commit();
            } catch (Exception var14) {
                this.em.getTransaction().rollback();
                var14.printStackTrace();
            }
        }

        long allRatings = (long) this.em.createQuery("SELECT COUNT(r) FROM PlaceRating r WHERE r.place.id=:placeId").setParameter("placeId", Long.valueOf(placeId)).getMaxResults();
        long allRatingsSumm = ((Long) ((Long) this.em.createQuery("SELECT SUM(r.rating) FROM PlaceRating r WHERE r.place.id=:placeId").getSingleResult())).longValue();
        int finalRating = Math.round((float) (allRatingsSumm / allRatings));

        try {
            this.em.getTransaction().begin();
            this.em.createQuery("UPDATE Place p SET p.placeFinalRating=:finalRating WHERE p.id=:placeId").setParameter("placeId", Long.valueOf(placeId)).setParameter("finalRating", Integer.valueOf(finalRating));
            this.em.getTransaction().commit();
        } catch (Exception var13) {
            this.em.getTransaction().rollback();
            var13.printStackTrace();
        }

        return Integer.valueOf(finalRating);
    }

    public long checkCredentials(String email, String userName) {
        return ((Long) this.em.createQuery("SELECT COUNT (e.id) FROM PlaceUser e WHERE e.userEmail=:email OR e.userName=:userName").setParameter("email", email).setParameter("userName", userName).getSingleResult()).longValue();
    }

    @Transactional
    public void newToken(VerificationToken tokens, PlaceUser user) {
        tokens.setUser(user);
        this.em.persist(tokens);
        this.em.flush();
        user.setTokens(tokens);
        this.em.flush();
    }

    public VerificationToken findToken(String token) {
        return (VerificationToken) this.em.createQuery("SELECT e FROM VerificationToken e WHERE e.token=:token").setParameter("token", token).getSingleResult();
    }

    public PlaceUser getUserByName(String ownerName) {
        return (PlaceUser) this.em.createQuery("SELECT e FROM PlaceUser e WHERE e.userEmail=:userName").setParameter("userName", ownerName).getSingleResult();
    }

    @Transactional
    public void grandUserAuthorities(PlaceUser user, Authorities authorities) {
        authorities.setUser(user);
        this.em.persist(authorities);
        this.em.flush();
        user.getAuthorities().add(authorities);
    }

    public void deleteToken(long tokenId) {
        this.em.createQuery("DELETE FROM VerificationToken e WHERE e.id=:id").setParameter("id", Long.valueOf(tokenId)).executeUpdate();
    }

    public byte[] getPlaceMainImage(long id) {
        return ((PlacePhoto) this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id").setParameter("name", "main").setParameter("id", Long.valueOf(id)).getSingleResult()).getBody();
    }

    public byte[] getPlaceSmallImage(long id) {
        return ((PlacePhoto) this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id").setParameter("name", "small").setParameter("id", Long.valueOf(id)).getSingleResult()).getBody();
    }

    @Transactional
    public void newMenu(PlaceMenu menu, Place place) {
        menu.setMenuPlace(place);
        this.em.persist(menu);
        this.em.flush();
        place.getPlaceMenu().add(menu);
        this.em.flush();
    }

    @Transactional
    public void saveMenuPhoto(PlaceMenu menu, PlaceMenuPhoto photo) {
        photo.setMenu(menu);
        this.em.persist(photo);
        this.em.flush();
        List menuPhotos = menu.getPhoto();
        if (menuPhotos != null) {
            menuPhotos.add(photo);
            menu.setPhoto(menuPhotos);
        } else {
            menu.setPhoto(Collections.singletonList(photo));
        }

        this.em.flush();
    }

    public void newPlaceMenuService(PlaceMenu menu, PlaceMenuOptionalService service) {
        service.setMenu(menu);
        this.em.persist(service);
        this.em.flush();
        menu.getServices().add(service);
        this.em.flush();
    }

    public byte[] getMenuSmallImage(long id) {
        return ((PlaceMenuPhoto) this.em.createQuery("SELECT e FROM PlaceMenuPhoto e WHERE e.menu.id=:id AND e.name=:name").setParameter("id", Long.valueOf(id)).setParameter("name", "small").getSingleResult()).getBody();
    }

    @Transactional
    public void addPlacePhoto(PlacePhoto photo, Place place) {
        photo.setPlace(place);
        this.em.persist(photo);
        this.em.flush();
        List photos = place.getPlacePhotos();
        if (photos != null) {
            place.getPlacePhotos().add(photo);
        } else {
            place.setPlacePhotos(Collections.singletonList(photo));
        }

        this.em.flush();
    }

    @Transactional
    public void registration(PlaceUser user, UserAddress address) {
        address.setUser(user);
        this.em.persist(address);
        this.em.flush();
        user.setAddress(address);
        this.em.flush();
    }

    @Transactional
    public void newPlaceMenuOptionalService(PlaceMenuOptionalService service, PlaceMenu menu) {
        service.setMenu(menu);
        this.em.persist(service);
        this.em.flush();
        List<PlaceMenuOptionalService> services = menu.getServices();
        if (services == null) {
            menu.setServices(Collections.singletonList(service));
        } else {
            services.add(service);
            menu.setServices(services);
        }

        this.em.flush();
    }

    @Override
    public PlaceMenuOptionalService getMenuServicesById(Long l) {
        return em.find(PlaceMenuOptionalService.class, l);
    }

    @Override
    @Transactional
    public void newOrder(PlaceUser user, Place placeById, PlaceMenu menuById, ArrayList<PlaceMenuOptionalService> servicesList) {
        Order order = new Order();
        order.setUser(user);
        order.setPlace(placeById);
        order.setMenu(menuById);
        order.setServices(servicesList);
        em.flush();
        menuById.setOrders(setIfNotNull(menuById, menuById.getOrders(), Order.class));
        placeById.setOrders(setIfNotNull(placeById, placeById.getOrders(), Order.class));
        user.setOrders(setIfNotNull(user, user.getOrders(), Order.class));
        for (PlaceMenuOptionalService s : servicesList) {
            s.setOrders(setIfNotNull(s, s.getOrders(), Order.class));
            em.merge(s);
        }
        em.flush();
    }

    private <T> List<T> setIfNotNull(Object object, List<T> list, Class<T> type) {
        if (list == null) return Collections.singletonList(type.cast(object));
        else {
            list.add(type.cast(object));
            return list;
        }
    }
}
