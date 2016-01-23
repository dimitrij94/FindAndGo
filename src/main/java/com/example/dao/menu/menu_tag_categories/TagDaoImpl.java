package com.example.dao.menu.menu_tag_categories;

import com.example.dao.DBBean;
import com.example.domain.menu.PlaceMenu;
import com.example.domain.menu.PlaceMenuTags;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dmitrij on 22.01.2016.
 */

@Repository
public class TagDaoImpl extends DBBean implements TagDAO {

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
}
