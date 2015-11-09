package com.example.services.registration;


import com.example.pojo.dto.UserCreateForm;
import com.example.domain.registration.Authorities;
import com.example.domain.registration.VerificationToken;
import com.example.domain.addresses.UserAddress;
import com.example.domain.PlaceUser;
import com.example.services.mail.MailService;
import com.example.dao.IDBBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Dmitrij on 23.09.2015.
 */
@Component
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    IDBBean dao;

    @Autowired
    MailService mailService;

    @Override
    public PlaceUser resendRegistrationToken(String email){
        PlaceUser user = dao.getUserByName(email.toLowerCase());
        dao.deleteToken(user.getToken().getId());
        saveRegistrationToken(user);
        return user;
    }

    @Override
    public void saveRegistrationToken(PlaceUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken tokens = new VerificationToken();
        tokens.setToken(token);
        dao.newToken(tokens, user);
    }


    @Override
    public boolean confirmToken(String token) {
        VerificationToken tokens = dao.findToken(token);
        if (tokens != null) {
            if (tokens.getDate().getTime() > Calendar.getInstance().getTime().getTime()) {
                PlaceUser user = tokens.getUser();
                dao.deleteToken(user.getToken().getId());
                user.setEnabled(true);
                dao.grandUserAuthorities(user, new Authorities(Authorities.Roles.ROLE_USER.name()));
                return true;
            }
            return false;
        }
        return false;
    }


    private boolean checkCredetials(String email, String userName) {
        return dao.checkCredentials(email, userName)==0;
    }

    @Override
    public PlaceUser register(UserCreateForm blank, HttpServletRequest request) {
        if (checkCredetials(blank.getUserEmail(), blank.getUserName())) {
            PlaceUser user = new PlaceUser();

            user.setUserEmail(blank.getUserEmail());
            user.setUserName(blank.getUserName());
            user.setUserPass(blank.getUserPass());

            user.setSex(blank.getSex());
            user.setName(blank.getName());
            user.setSname(blank.getSname());
            user.setAge(blank.getAge());
            user.setEnabled(false);

            dao.registration(user, new UserAddress(blank.getAddress()));
            saveRegistrationToken(user);
            mailService.confirmEmailMessage(user, request);
            return user;
        }
        return null;
    }

    private List<String> lookForMatches(List<String> data, String query) {
        return data.stream().filter(c ->
                c.toLowerCase().startsWith(query.toLowerCase())).collect(Collectors.toList());
    }
}
