package com.example.dao.menu;

import com.example.dao.DBBean;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuRating;
import com.example.domain.menu.PlaceMenuTags;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitrij on 21.01.2016.
 */
@Repository
public class MenuDaoImpl extends DBBean implements MenuDAO {

    @Override
    public PlaceMenu getMenuById(long id) {
        return this.em.find(PlaceMenu.class, id);
    }

    @Override
    @Transactional
    public void newMenu(PlaceMenu menu, Place place, List<PlaceMenuTags> tags) {
        menu.setMenuPlace(place);
        menu.setMenuTags(tags);
        this.em.persist(menu);
        for (PlaceMenuTags t : tags) {
            t.setMenus(setAsList(t.getMenus(), menu));
            em.merge(t);
        }
        place.setPlaceMenu(setAsList(place.getPlaceMenu(), menu));
    }







    @Override
    public boolean isMenuFromPlace(PlaceMenu menu, Place place) {
        return ((long) em.createQuery("SELECT COUNT(e.id) FROM Place e INNER JOIN e.placeMenu m " +
                "WHERE e.id=:pId AND m.id=:mId")
                .setParameter("pId", place.getId())
                .setParameter("mId", menu.getId())
                .getSingleResult()) == 0L;

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
    public int getMenuFinalRating(PlaceMenu placeMenu) {
        return (int) Math.round((double) em.createQuery("SELECT AVG (e.rating) FROM PlaceMenu m inner join " +
                "m.menuRatings e WHERE m.id=:mId")
                .setParameter("mId", placeMenu.getId())
                .getSingleResult());
    }

    @Override
    @Transactional
    public void updateMenuFinalRating(PlaceMenu placeMenu, int menuFinalRating) {
        placeMenu.setMenuFinalRating(menuFinalRating);
        em.merge(placeMenu);
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

    @Override
    public List<PlaceMenu> getBestMenus(int from, int to) {
        return em.createQuery("SELECT e FROM PlaceMenu e ORDER BY e.userOrderses.size DESC, e.menuFinalRating DESC ", PlaceMenu.class)
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();
    }
}
