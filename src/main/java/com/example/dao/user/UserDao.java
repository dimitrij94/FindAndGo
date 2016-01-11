package com.example.dao.user;

import com.example.domain.users.PlaceUser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Dmitrij on 08.01.2016.
 */
@Component
public class UserDao implements UserDaoI{
    @PersistenceContext
    EntityManager em;
    @Override
    public PlaceUser getUserById(long id){
        return em.find(PlaceUser.class,id);
    }

}
