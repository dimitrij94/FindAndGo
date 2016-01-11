//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.dao;

import com.example.domain.Place;
import com.example.domain.PlaceSpeciality;
import com.example.domain.UserOrders;
import com.example.domain.addresses.PlaceAddress;
import com.example.domain.addresses.UserAddress;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuOptionalService;
import com.example.domain.menu.PlaceMenuTags;
import com.example.domain.photos.PlaceEmployeePhoto;
import com.example.domain.photos.PlaceMenuPhoto;
import com.example.domain.photos.PlacePhoto;
import com.example.domain.photos.PlaceUserPhoto;
import com.example.domain.ratings.PlaceMenuRating;
import com.example.domain.registration.VerificationToken;
import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.domain.users.employee.EmployeePauses;
import com.example.domain.users.employee.PlaceEmployee;
import com.example.interfaces.PhotoCotainable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DBBean implements IDBBean {

    @PersistenceContext
    EntityManager em;


    public DBBean() {
    }

    public PlacePhoto getUserPhoto() {
        return em.createQuery("SELECT e FROM PlacePhoto e WHERE e.id=:id", PlacePhoto.class)
                .getSingleResult();
    }

    public Place getPlaceById(long id) {
        return this.em.find(Place.class, id);
    }

    public Place getOwnerPlaceById(long id, PlaceOwner owner) {
        return this.em.createQuery("SELECT e FROM PlaceOwner e WHERE e.id=:id", Place.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public PlaceMenu getMenuById(long id) {
        return  this.em.find(PlaceMenu.class, id);
    }

    public PlaceUser authorization(String email, String pass) {
        return this.em.createQuery("SELECT e FROM PlaceUser e " +
                "WHERE (e.email=:email OR e.userName=:email) AND e.password=:pass", PlaceUser.class)
                .setParameter("email", email)
                .setParameter("pass", pass)
                .getSingleResult();
    }

    @Transactional
    public void addNewPlace(Place place, PlaceAddress placeAddress, PlaceOwner owner, PlaceSpeciality speciality) {
        place.setPlaceOwner(owner);
        place.setPlaceSpeciality(speciality);
        this.em.persist(place);
        speciality.setPlace(setAsList(speciality.getPlace(), place));
        this.em.flush();
        placeAddress.setPlace(place);
        this.em.persist(placeAddress);
        place.setAddress(placeAddress);
        this.em.flush();
        owner.getPlaces().add(place);
        this.em.flush();
    }


    public List<Place> getMainPlaces() {
        List<Place> mainPlaces =
                this.em.createQuery("SELECT e FROM Place e ORDER BY e.placeFollowersNum DESC, e.placeFinalRating DESC", Place.class)
                        .getResultList();
        if (mainPlaces.size() > 15) {
            mainPlaces.subList(0, 20);
        }
        return mainPlaces;
    }


    public long checkCredentials(String email, String userName) {
        return (Long) this.em.createQuery("SELECT COUNT (e.id) FROM PlaceUser e WHERE e.email=:email OR e.userName=:userName").setParameter("email", email).setParameter("userName", userName).getSingleResult();
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
        return (PlaceUser) this.em.createQuery("SELECT e FROM PlaceUser e WHERE e.email=:userName")
                .setParameter("userName", ownerName).getSingleResult();
    }

    @Transactional
    public void deleteToken(VerificationToken token) {
        em.remove(token);
    }

    @Cacheable("photo-cache")
    public byte[] getPlaceImageBodyByName(long id, String name) {
        return (this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id", PlacePhoto.class)
                .setParameter("name", name)
                .setParameter("id", id).getSingleResult())
                .getBody();
    }

    @Cacheable("photo-cache")
    @Override
    public PlacePhoto getPlacePhotoByName(String name, long id) {
        return this.em.createQuery("SELECT e FROM PlacePhoto e WHERE e.name=:name AND e.place.id=:id", PlacePhoto.class)
                .setParameter("name", name)
                .setParameter("id", id).getSingleResult();
    }

    @Transactional
    public void newMenu(PlaceMenu menu, Place place, List<PlaceMenuTags> tags) {
        menu.setMenuPlace(place);
        menu.setMenuTags(tags);
        this.em.persist(menu);
        for (PlaceMenuTags t : tags)
            t.setMenus(setAsList(t.getMenus(), menu));
        this.em.flush();
        place.getPlaceMenu().add(menu);
        this.em.flush();
    }

    @Transactional
    public void addMenuPhoto(byte[] body, PhotoCotainable domain, String name) {
        PlaceMenu menu = (PlaceMenu) domain;
        PlaceMenuPhoto photo = new PlaceMenuPhoto(body, name, menu);
        this.em.persist(photo);
        this.em.flush();
        menu.setPhoto(setAsList(menu.getPhoto(), photo));
        em.merge(menu);
    }

    @Transactional
    public void newPlaceMenuService(PlaceMenu menu, PlaceMenuOptionalService service) {
        service.setMenu(menu);
        this.em.persist(service);
        this.em.flush();
        menu.getServices().add(service);
        this.em.flush();
    }

    public byte[] getMenuImageBodyByName(long id, String name) {
        return (this.em.createQuery("SELECT e FROM PlaceMenuPhoto e WHERE e.menu.id=:id AND e.name=:name", PlaceMenuPhoto.class)
                .setParameter("id", id)
                .setParameter("name", name)
                .getSingleResult())
                .getBody();
    }

    @Override
    public PlaceMenuPhoto getMenuPhotoByName(String name, long id) {
        return this.em.createQuery("SELECT e FROM PlaceMenuPhoto e WHERE e.menu.id=:id AND e.name=:name", PlaceMenuPhoto.class)
                .setParameter("id", id)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Transactional
    public void addPlacePhoto(byte[] body, PhotoCotainable domain, String name) {
        Place place = (Place) domain;
        PlacePhoto photo = new PlacePhoto(body, name);
        photo.setPlace(place);
        this.em.persist(photo);
        this.em.flush();
        place.setPlacePhotos(savePlacePhotoAsList(place.getPlacePhotos(), photo));
    }

    private List<PlacePhoto> savePlacePhotoAsList(List<PlacePhoto> list, PlacePhoto photo) {
        if (list == null) {
            ArrayList<PlacePhoto> singeltonList = new ArrayList<>(2);
            singeltonList.add(photo);
            return singeltonList;
        } else {
            list.add(photo);
            return list;
        }
    }

    @Transactional
    @Override
    public PlaceUser registration(PlaceUser user, UserAddress address) {
        em.persist(user);
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
    public UserOrders newOrder(PlaceUser user,
                               Place place,
                               PlaceMenu menu,
                               List<Long> servicesList,
                               PlaceEmployee employee) {
        UserOrders userOrders = new UserOrders();
        userOrders.setUser(user);
        userOrders.setPlace(place);
        userOrders.setMenu(menu);
        userOrders.setEmployee(employee);
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

        user.setUserOrders(getOrdersList(userOrders, user.getUserOrders()));
        em.merge(user);
        place.setUserOrderses(getOrdersList(userOrders, place.getUserOrderses()));
        em.merge(place);
        menu.setUserOrderses(getOrdersList(userOrders, menu.getUserOrderses()));
        em.merge(menu);
        employee.setOrders(getOrdersList(userOrders, employee.getOrders()));
        em.flush();
        return userOrders;
    }

    @Override
    public List<UserOrders> getUserPlaceOrders(long userId, long placeId) {
        return em.createQuery("SELECT e FROM UserOrders e " +
                "WHERE e.place.id=:placeId AND e.user.id=:userId AND e.active=false", UserOrders.class)
                .setParameter("placeId", placeId)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Place> getPlacesWithUserOrder(PlaceUser user) {
        return em.createQuery("SELECT DISTINCT p FROM Place p,PlaceUser u INNER JOIN u.userOrders o " +
                "WHERE o.user.id=:userId AND o.place.id=p.id AND o.active=false", Place.class)
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
    public Place getOwnerPlace(long placeId, PlaceOwner user) {
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
    public byte[] getUserPhotoBodyByName(String name, long id) {
        return em.createQuery("SELECT e FROM PlaceUserPhoto e WHERE e.user.id=:userId AND e.name=:name", PlaceUserPhoto.class)
                .setParameter("userId", id)
                .setParameter("name", name)
                .getSingleResult()
                .getBody();
    }

    @Override
    public PlaceUserPhoto getUserPhotoByName(String name, long id) {
        return em.createQuery("SELECT e FROM PlaceUserPhoto e WHERE e.user.id=:userId AND e.name=:name", PlaceUserPhoto.class)
                .setParameter("userId", id)
                .setParameter("name", name)
                .getSingleResult();
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
    public long isPlaceUser(Long id, Long id1) {
        return (long) em.createQuery("SELECT COUNT (e.id) FROM Place e inner join e.placeUsers u WHERE u.id=:uId AND e.id=:pId")
                .setParameter("uId", id1)
                .setParameter("pId", id)
                .getSingleResult();
    }

    @Override
    public int getPlaceFinalRating(Place place) {
        return (int) Math.round((double) ((em.createQuery("SELECT AVG (m.menuFinalRating) FROM Place e, e.placeMenu m WHERE e.id=:pId")
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
        order.setActive(b);
        em.merge(order);
    }

    @Override
    public long isUserLikedPlace(PlaceUser user, Place p) {
        return (long) em.createQuery("SELECT COUNT (e) FROM Place e INNER JOIN e.placeUsers u WHERE u.id=:uId AND e.id=:pId")
                .setParameter("uId", user.getId())
                .setParameter("pId", p.getId())
                .getSingleResult();
    }

    @Override
    public PlaceOwner getOwnerByName(String s) {
        return (PlaceOwner) em.createQuery("SELECT e FROM PlaceOwner e WHERE e.name=:n OR e.email=:e")
                .setParameter("n", s)
                .setParameter("e", s)
                .getSingleResult();
    }

    @Override
    public void updateMenuUserMenuRating(String comment, int rating, PlaceUser user, PlaceMenu placeMenu) {
        em.createQuery("UPDATE PlaceMenuRating e set e.comment=:comment,e.rating=:rating,e.user=:user,e.menu=:menu " +
                "WHERE e.user.id=:uId AND e.menu.id=:mId")
                .setParameter("comment", comment)
                .setParameter("rating", rating)
                .setParameter("user", user)
                .setParameter("menu", placeMenu)
                .setParameter("uId", user.getId())
                .setParameter("mId", placeMenu.getId())
                .executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserOrders> getEmployeeTodayOrders(PlaceEmployee employee, LocalDate date) {
        return (List<UserOrders>) em.createQuery("SELECT e FROM UserOrders e " +
                "WHERE e.employee.id=:eId AND e.startTime=:oD ORDER BY e.startTime")
                .setParameter("eId", employee.getId())
                .setParameter("oD", date)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeePauses> getEmployeeTodayPauses(PlaceEmployee employee, LocalDateTime localDate) {
        return (List<EmployeePauses>) em.createQuery("SELECT e FROM EmployeePauses e " +
                "WHERE e.start =:date AND e.employee.id=:eId ORDER BY e.start")
                .setParameter("eId", employee.getId())
                .setParameter("date", localDate)
                .getResultList();
    }

    @Override
    public List<PlaceMenu> getBestMenus(int from, int to) {
        return em.createQuery("SELECT e FROM PlaceMenu e ORDER BY e.userOrderses.size DESC, e.menuFinalRating DESC ", PlaceMenu.class)
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();
    }

    @Override
    public PlaceEmployee getEmployeeById(long employeeID) {
        return em.find(PlaceEmployee.class, employeeID);
    }

    @Override
    public UserOrders activateOrder(UserOrders orders) {
        orders.setActive(true);
        em.merge(orders);
        return orders;
    }

    @Override
    public UserOrders findOrderById(long id, PlaceUser user) {
        return em.createQuery("SELECT e FROM UserOrders e WHERE e.user=:user AND e.id=:id", UserOrders.class)
                .setParameter("user", user)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<PlaceMenu> findMenuWithTag(List<Long> tagsId, int startFrom, int endAt) {
        return em.createQuery("SELECT e from PlaceMenu e inner join e.menuTags t WHERE t.id IN :tId", PlaceMenu.class)
                .setParameter("tId", tagsId)
                .setFirstResult(startFrom)
                .setMaxResults(endAt)
                .getResultList();
    }

    @Override
    public List<PlaceMenuTags> findTagsByName(String t) {
        return em.createQuery("SELECT e FROM PlaceMenuTags e WHERE e.name LIKE :pattern", PlaceMenuTags.class)
                .setParameter("pattern", t + "%")
                .setMaxResults(5)
                .getResultList();
    }

    @Override
    public PlaceMenuTags findTagByName(String name) {
        return em.createQuery("SELECT e FROM PlaceMenuTags e WHERE e.name=:name", PlaceMenuTags.class)
                .setParameter("name", name)
                .setMaxResults(5)
                .getSingleResult();
    }

    @Override
    public PlaceMenuTags newTag(String t) {
        PlaceMenuTags tag = new PlaceMenuTags(t);
        em.persist(tag);
        return tag;
    }

    @Override
    public PlaceEmployee getEmployeeByName(String s) {
        return em.createQuery("SELECT e FROM PlaceEmployee e WHERE e.name =:name", PlaceEmployee.class)
                .setParameter("name", s)
                .getSingleResult();
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
    public void addEmployeePhoto(byte[] body, PhotoCotainable domain, String name) {
        PlaceEmployee employee = (PlaceEmployee) domain;
        PlaceEmployeePhoto photo = new PlaceEmployeePhoto();
        photo.setName(name);
        photo.setEmployee(employee);
        photo.setBody(body);
        em.persist(photo);
        em.flush();
        employee.setPhoto(setAsList(employee.getPhoto(), photo));
    }

    @Override
    public void addPlaceUserPhoto(byte[] body, PhotoCotainable domain, String name) {
        PlaceUser user = (PlaceUser) domain;
        PlaceUserPhoto photo = new PlaceUserPhoto(body, user, name);
        em.persist(photo);
        user.setPhotos(setAsList(user.getPhotos(), photo));
    }


    private <T> List<T> setAsList(List<T> list, T object) {
        if (list == null) {
            return Collections.singletonList(object);
        } else {
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
