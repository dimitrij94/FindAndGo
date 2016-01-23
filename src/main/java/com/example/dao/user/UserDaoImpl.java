package com.example.dao.user;

import com.example.dao.DBBean;
import com.example.domain.users.PlaceUser;
import com.example.domain.users.PlaceUserPhoto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 08.01.2016.
 */
@Component
public class UserDaoImpl extends DBBean implements UserDaoI {

    @Override
    public PlaceUser getUserById(long id) {
        return em.find(PlaceUser.class, id);
    }

    @Override
    public long checkCredentials(String email, String userName) {
        return (Long) this.em.createQuery("SELECT COUNT (e.id) FROM PlaceUser e WHERE e.email=:email OR e.userName=:userName").setParameter("email", email).setParameter("userName", userName).getSingleResult();
    }



    @Transactional
    @Override
    public PlaceUser registration(PlaceUser user) {
        em.persist(user);
        em.flush();
        return user;
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
    public long isPlaceUser(Long id, Long id1) {
        return (long) em.createQuery("SELECT COUNT (e.id) FROM Place e inner join e.placeUsers u WHERE u.id=:uId AND e.id=:pId")
                .setParameter("uId", id1)
                .setParameter("pId", id)
                .getSingleResult();
    }



}
