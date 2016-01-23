package com.example.dao.authoriztion;

import com.example.domain.employee.PlaceEmployee;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.registration.VerificationToken;
import com.example.domain.users.PlaceUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dmitrij on 22.01.2016.
 */
public interface AuthorizationDAO  {
    @Transactional
    void deleteToken(VerificationToken token);

    @Transactional
    void newToken(VerificationToken tokens, PlaceUser user);

    VerificationToken findToken(String token);

    PlaceUser getUserByEmail(String ownerName);

    PlaceUser getUserByEmail(String email, String pass);

    PlaceEmployee getEmployeeByEmail(String s);

    PlaceOwner getOwnerByEmail(String s);
}
