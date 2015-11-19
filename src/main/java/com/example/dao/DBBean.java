//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.dao;

import com.example.domain.*;
import com.example.domain.addresses.PlaceAddress;
import com.example.domain.addresses.UserAddress;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.domain.photos.PlacePhoto;
import com.example.domain.photos.PlaceUserPhoto;
import com.example.domain.ratings.PlaceMenuRating;
import com.example.domain.ratings.PlaceRating;
import com.example.domain.registration.Authorities;
import com.example.domain.registration.VerificationToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        return (Place) this.em.find(Place.class, id);
    }

    public Place getOwnerPlaceById(long id, PlaceUser user) {
        return (Place) this.em.createQuery("SELECT e FROM PlaceUser.ownerPlaces e WHERE e.id=:id").setParameter("id", id).getSingleResult();
    }

    public PlaceMenu getMenuById(long id) {
        return (PlaceMenu) this.em.find(PlaceMenu.class, id);
    }

    public PlaceUser authorization(String email, String pass) {
        return (PlaceUser) this.em.createQuery("SELECT e FROM PlaceUser e WHERE (e.userEmail=:email OR e.userName=:email) AND e.userPass=:pass")
                .setParameter("email", email)
                .setParameter("pass", pass);
    }

    @Transactional
    public void addNewPlace(Place place, PlaceAddress placeAddress, PlaceUser owner, PlaceSpeciality speciality) {
        place.setPlaceOwner(owner);
        place.setPlaceSpeciality(setAsList(place.getPlaceSpeciality(), speciality));
        this.em.persist(place);
        speciality.setPlace(setAsList(speciality.getPlace(), place));
        this.em.flush();
        placeAddress.setPlace(place);
        this.em.persist(placeAddress);
        place.setAddress(placeAddress);
        this.em.flush();
        owner.getOwnerPlaces().add(place);

        Authorities authorities = getAuthority("ROLE_OWNER");
        authorities.setUser(setAsList(authorities.getUser(), owner));
        owner.setAuthority(Collections.singletonList(authorities));
        this.em.flush();
    }

    @Override
    public Authorities getAuthority(String name) {
        return (Authorities) em.createQuery("SELECT e FROM Authorities e WHERE e.authority=:name")
                .setParameter("name", name)
                .getSingleResult();
    }


    public ArrayList<PlaceEvent> getMainEvents() {
        ArrayList<PlaceEvent> mainEvents = (ArrayList<PlaceEvent>) em.createQuery("SELECT e FROM PlaceEvent  e order by e.users.size").getResultList();
        if (mainEvents.size() > 5) {
            mainEvents.subList(0, 4);
        }

        Comparator<PlaceEvent> comparator = (e1, e2) -> Integer.valueOf(e1.getUsers().size()).compareTo(e2.getUsers().size());
        mainEvents.sort(comparator);
        return mainEvents;
    }


    public List<Place> getMainPlaces() {
        List<Place> mainPlaces = this.em.createQuery("SELECT e FROM Place e ORDER BY e.placeFollowersNum DESC, e.placeFinalRating DESC")
                .getResultList();
        if (mainPlaces.size() > 15) {
            mainPlaces.subList(0, 20);
        }
        return mainPlaces;
    }


    public long checkCredentials(String email, String userName) {
        return (Long) this.em.createQuery("SELECT COUNT (e.id) FROM PlaceUser e WHERE e.userEmail=:email OR e.userName=:userName").setParameter("email", email).setParameter("userName", userName).getSingleResult();
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
        user.setAuthority(Collections.singletonList(authorities));
        this.em.flush();
    }

    @Transactional
    public void deleteToken(VerificationToken token) {
        em.remove(token);
    }

    public byte[] getPlaceMainImage(long id) {
        return ((PlacePhoto) this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id").setParameter("name", "main").setParameter("id", id).getSingleResult()).getBody();
    }

    public byte[] getPlaceSmallImage(long id) {
        return ((PlacePhoto) this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id").setParameter("name", "small").setParameter("id", id).getSingleResult()).getBody();
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
        List<PlaceMenuPhoto> menuPhotos = menu.getPhoto();
        if (menuPhotos != null) {
            menuPhotos.add(photo);
            menu.setPhoto(menuPhotos);
        } else {
            menu.setPhoto(Collections.singletonList(photo));
        }

        this.em.flush();
    }

    @Transactional
    public void newPlaceMenuService(PlaceMenu menu, PlaceMenuOptionalService service) {
        service.setMenu(menu);
        this.em.persist(service);
        this.em.flush();
        menu.getServices().add(service);
        this.em.flush();
    }

    public byte[] getMenuSmallImage(long id) {
        return ((PlaceMenuPhoto) this.em.createQuery("SELECT e FROM PlaceMenuPhoto e WHERE e.menu.id=:id AND e.name=:name").setParameter("id", id).setParameter("name", "small").getSingleResult()).getBody();
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
    @Override
    public PlaceUser registration(PlaceUser user, UserAddress address) {
        Authorities a = getAuthority("ROLE_USER");
        grandUserAuthorities(user,a);
        em.persist(user);
        a.setUser(setAsList(a.getUser(), user));
        address.setUser(user);
        this.em.persist(address);
        this.em.flush();
        user.setAddress(address);
        return user;
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
    public void newOrder(PlaceUser user, Place place, PlaceMenu menu, List<Long> servicesList) {

        UserOrders userOrders = new UserOrders();
        userOrders.setUser(user);
        userOrders.setPlace(place);
        userOrders.setMenu(menu);
        em.persist(userOrders);
        em.flush();
        if (servicesList != null) {
            for (long sId : servicesList) {
                PlaceMenuOptionalService services = getMenuServicesById(sId);
                userOrders.setServices(getServiceAsList(services, userOrders.getServices()));
                em.flush();
                services.setUserOrderses(getOrdersList(userOrders, services.getUserOrderses()));
            }
        }

        user.setUserOrderses(getOrdersList(userOrders, user.getUserOrderses()));
        em.merge(user);
        place.setUserOrderses(getOrdersList(userOrders, place.getUserOrderses()));
        em.merge(place);
        menu.setUserOrderses(getOrdersList(userOrders, menu.getUserOrderses()));
        em.merge(menu);

        em.flush();
    }

    @Override
    public List<UserOrders> getUserPlaceOrders(long userId, long placeId) {
        return em.createQuery("SELECT e FROM UserOrders e " +
                "WHERE e.place.id=:placeId AND e.user.id=:userId AND e.isDone=false")
                .setParameter("placeId", placeId)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Place> getPlacesWithUserOrder(PlaceUser user) {
        return em.createQuery("SELECT DISTINCT p FROM Place p,PlaceUser u INNER JOIN u.userOrderses o " +
                "WHERE o.user.id=:userId AND o.place.id=p.id AND o.isDone=false")
                .setParameter("userId", user.getId())
                .getResultList();
    }

    @Override
    public boolean isMenuFromPlace(PlaceMenu menu, Place place) {
        return ((int) em.createQuery("SELECT COUNT(e.id) FROM Place e INNER JOIN e.placeMenu m " +
                "WHERE e.id=:pId AND m.id=:mId")
                .setParameter("pId", place.getId())
                .setParameter("mId", menu.getId())
                .getSingleResult()) == 0;

    }

    @Override
    public Place getOwnerPlace(long placeId, PlaceUser user) {
        return (Place) em.createQuery("SELECT e FROM Place e WHERE e.id=:id AND e.placeOwner.id=:uId")
                .setParameter("id", placeId)
                .setParameter("uId", user.getId())
                .getSingleResult();
    }

    @Override
    public PlaceUser getUserById(long i) {
        return em.find(PlaceUser.class, i);
    }

    @Override
    @Transactional
    public void newPlaceLike(PlaceUser user, Place place) {
        Place p = getPlaceById(place.getId());
        user.setUserPlaces(getUserPlacesAsList(p, user.getUserPlaces()));
        setPlaceUsers(p.getPlaceUsers(), p, user);
        p.setPlaceFollowersNum(p.getPlaceFollowersNum() + 1);
        em.flush();
    }

    private void setPlaceUsers(List<PlaceUser> users, Place p, PlaceUser user) {
        if (users == null) {
            p.setPlaceUsers(Collections.singletonList(user));
        } else {
            users.add(user);
        }
        em.flush();
    }

    @Override
    @Transactional
    public void removeLike(PlaceUser user, long placeId) {
        Place place = getPlaceById(placeId);
        user.getUserPlaces().remove(place);
        place.getPlaceUsers().remove(user);
        place.setPlaceFollowersNum(place.getPlaceFollowersNum() - 1);
        em.flush();
    }

    @Override
    public byte[] getUserPhoto(long id, String name) {
        return ((PlaceUserPhoto) em.createQuery("SELECT e FROM PlaceUserPhoto e " +
                "WHERE e.user.id=:userId AND e.name=:name")
                .setParameter("userId", id)
                .setParameter("name", name)).getBody();
    }

    @Override
    public long countUserMenuRatings(long menuId, long userId) {
        return (long) em.createQuery("SELECT COUNT (e.id)FROM PlaceMenuRating e " +
                "WHERE e.user.id=:userId AND e.menu.id=:menuId")
                .setParameter("userId", userId)
                .setParameter("menuId", menuId)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void newPlaceMenuRating(String comment, int rating, PlaceUser user, PlaceMenu placeMenu) {
        PlaceMenuRating menuRating = new PlaceMenuRating()
                .rating(rating)
                .comment(comment)
                .menu(placeMenu)
                .user(user);
        em.persist(menuRating);
        em.flush();
        placeMenu.setMenuRatings(setAsList(placeMenu.getMenuRatings(), menuRating));
        user.setUserMenuRatings(setAsList(user.getUserMenuRatings(), menuRating));
    }

    @Override
    public void deleteComment(PlaceUser user, PlaceMenu placeMenu) {
        em.createQuery("DELETE FROM PlaceMenuRating e " +
                "WHERE e.menu.id=:menuId AND e.user.id=:userId")
                .setParameter("menuId", placeMenu.getId())
                .setParameter("userId", user.getId())
                .executeUpdate();
    }

    @Override
    public PlaceSpeciality getPlaceSpeciality(String specialization) {
        return (PlaceSpeciality) em.createQuery("SELECT e FROM PlaceSpeciality e WHERE e.speciality=:s")
                .setParameter("s", specialization)
                .getSingleResult();
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
    public long countUserPlaceRatings(long pId, Long id) {
        return (long) em.createQuery("SELECT COUNT(e.id) FROM PlaceRating e " +
                "WHERE e.user.id=:uId AND e.place.id=:pId")
                .setParameter("uId", id)
                .setParameter("pId", pId).getSingleResult();
    }

    @Override
    @Transactional
    public void newPlaceRating(Place place, PlaceUser user, int rating) {
        PlaceRating placeRating = new PlaceRating();
        placeRating.setPlace(place);
        placeRating.setUser(user);
        placeRating.setRating(rating);
        em.persist(placeRating);
        em.flush();
        user.setUserPlacesRating(setAsList(user.getUserPlacesRating(), placeRating));
        place.setPlaceRatings(setAsList(place.getPlaceRatings(), placeRating));
        em.flush();
    }

    @Override
    @Transactional
    public void deleteUserPlaceRating(Long id, long pId) {
        em.createQuery("DELETE FROM PlaceRating e WHERE e.user.id=:id AND e.place.id=:pId")
                .setParameter("id", id)
                .setParameter("pId", pId)
                .executeUpdate();
    }

    @Override
    public long isPlaceUser(Long id, Long id1) {
        return (long) em.createQuery("SELECT COUNT (e.id) FROM Place e inner join e.placeUsers u WHERE u.id=:uId AND e.id=:pId")
                .setParameter("uId", id1)
                .setParameter("pId", id)
                .getSingleResult();
    }

    @Override
    public int getPlaceFinalRating(Place place) {
        return (int) Math.round((double) ((em.createQuery("SELECT AVG (r.rating) FROM Place e inner join e.placeRatings r " +
                "WHERE e.id=:pId")
                .setParameter("pId", place.getId())
                .getSingleResult())));

    }

    @Override
    public UserOrders getOrder(long id) {
        return em.find(UserOrders.class, id);
    }

    @Override
    public int getMenuFinalRating(PlaceMenu placeMenu) {
        return (int) Math.round((double) em.createQuery("SELECT AVG (e.rating) FROM PlaceMenu m inner join " +
                "m.menuRatings e WHERE m.id=:mId")
                .setParameter("mId", placeMenu.getId())
                .getSingleResult());
    }

    @Override
    @Transactional
    public void updatePlaceRating(Place place, int finalRating) {
        place.setPlaceFinalRating(finalRating);
        em.merge(place);
    }

    @Override
    @Transactional
    public void updateMenuFinalRating(PlaceMenu placeMenu, int menuFinalRating) {
        placeMenu.setMenuFinalRating(menuFinalRating);
        em.merge(placeMenu);
    }

    @Override
    @Transactional
    public void setOrderComplete(UserOrders order, boolean b) {
        order.setIsDone(b);
        em.merge(order);
    }

    @Override
    public long isUserLikedPlace(PlaceUser user, Place p) {
        return (long) em.createQuery("SELECT COUNT (e) FROM Place e INNER JOIN e.placeUsers u WHERE u.id=:uId AND e.id=:pId")
                .setParameter("uId", user.getId())
                .setParameter("pId", p.getId())
                .getSingleResult();
    }

    private <T> List<T> setAsList(List<T> list, T object) {
        if (list == null) return Collections.singletonList(object);
        else {
            list.add(object);
            return list;
        }
    }

    private List<Place> getUserPlacesAsList(Place p, List<Place> list) {
        if (list == null) return Collections.singletonList(p);
        else {
            list.add(p);
            return list;
        }

    }

    private List<PlaceMenuOptionalService> getServiceAsList(PlaceMenuOptionalService s,
                                                            List<PlaceMenuOptionalService> list) {
        if (list == null) return Collections.singletonList(s);
        else {
            list.add(s);
            return list;
        }
    }

    private List<UserOrders> getOrdersList(UserOrders userOrders, List<UserOrders> list) {
        if (list == null) return Collections.singletonList(userOrders);
        else {
            list.add(userOrders);
            return list;
        }
    }
}
