package com.example.dao.authoriztion;

import com.example.dao.DBBean;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.registration.OwnerVerificationToken;
import com.example.domain.registration.VerificationToken;
import com.example.domain.users.PlaceUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */
@Repository
public class AuthoriztionDaoImpl extends DBBean implements AuthorizationDAO {

    @Transactional
    @Override
    public void deleteToken(VerificationToken token) {
        em.remove(token);
    }

    @Override
    @Transactional
    public void newToken(VerificationToken tokens, PlaceUser user) {
        tokens.setUser(user);
        this.em.persist(tokens);
        this.em.flush();
        user.setTokens(tokens);
        this.em.flush();
    }

    @Override
    public VerificationToken findToken(String token) {
        return (VerificationToken) this.em.createQuery("SELECT e FROM VerificationToken e WHERE e.token=:token").setParameter("token", token).getSingleResult();
    }

    @Override
    public PlaceUser getUserByEmail(String ownerName) {
        return this.em.createQuery("SELECT e FROM PlaceUser e WHERE e.email=:userName",PlaceUser.class)
                .setParameter("userName", ownerName)
                .getSingleResult();
    }

    @Override
    public PlaceUser getUserByEmail(String email, String pass) {
        return this.em.createQuery("SELECT e FROM PlaceUser e " +
                "WHERE (e.email=:email) AND e.password=:pass", PlaceUser.class)
                .setParameter("email", email)
                .setParameter("pass", pass)
                .getSingleResult();
    }

    @Override
    public PlaceEmployee getEmployeeByEmail(String s) {
        return em.createQuery("SELECT e FROM PlaceEmployee e WHERE e.email =:name", PlaceEmployee.class)
                .setParameter("name", s)
                .getSingleResult();
    }

    @Override
    public PlaceOwner getOwnerByEmail(String s) {
        PlaceOwner owner =  em.createQuery("SELECT e FROM PlaceOwner e WHERE e.email=:e",PlaceOwner.class)
                .setParameter("e", s)
                .getSingleResult();
        return owner;
    }

    @Override
    public void deleteOwnerToken(OwnerVerificationToken token) {
        em.remove(token);
    }

    @Override
    @Transactional
    public void newOwnerAuthorizationToken(OwnerVerificationToken tokens, PlaceOwner owner) {
        tokens.setOwner(owner);
        em.persist(tokens);
        owner.setToken(tokens);
        em.merge(owner);
        em.flush();
    }

    @Override
    public OwnerVerificationToken findOwnerToken(String token) {
        return em.createQuery("SELECT e FROM OwnerVerificationToken e WHERE e.token=:token",OwnerVerificationToken.class)
                .setParameter("token",token)
                .getSingleResult();

    }
}
