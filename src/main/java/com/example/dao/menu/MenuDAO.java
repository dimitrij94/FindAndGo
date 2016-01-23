package com.example.dao.menu;

import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuTags;
import com.example.domain.place.Place;
import com.example.domain.users.PlaceUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dmitrij on 21.01.2016.
 */
public interface MenuDAO {
    PlaceMenu getMenuById(long id);

    @Transactional
    void newMenu(PlaceMenu menu, Place place, List<PlaceMenuTags> tags);



    boolean isMenuFromPlace(PlaceMenu menu, Place place);

    long countUserMenuRatings(long menuId, long userId);

    @Transactional
    void newPlaceMenuRating(String comment, int rating, PlaceUser user, PlaceMenu placeMenu);

    void deleteComment(PlaceUser user, PlaceMenu placeMenu);

    int getMenuFinalRating(PlaceMenu placeMenu);

    @Transactional
    void updateMenuFinalRating(PlaceMenu placeMenu, int menuFinalRating);

    void updateMenuUserMenuRating(String comment, int rating, PlaceUser user, PlaceMenu placeMenu);

    List<PlaceMenu> getBestMenus(int from, int to);
}
