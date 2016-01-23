package com.example.dao.menu.menu_tag_categories;

import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuTags;

import java.util.List;

/**
 * Created by Dmitrij on 22.01.2016.
 */
public interface TagDAO {
    List<PlaceMenu> findMenuWithTag(List<Long> tagsId, int startFrom, int endAt);

    List<PlaceMenuTags> findTagsByName(String t);

    PlaceMenuTags findTagByName(String name);

    PlaceMenuTags newTag(String t);
}
