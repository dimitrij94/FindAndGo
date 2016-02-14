package com.example.services.registration;


import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.dao.user.UserDaoI;
import com.example.domain.registration.VerificationToken;
import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.UserDTO;
import com.example.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Dmitrij on 23.09.2015.
 */
@Component
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    UserDaoI dao;

    @Autowired
    AuthorizationDAO authorizationDAO;

    @Autowired
    MailService mailService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;




    @Override
    public PlaceUser resendRegistrationToken(String email) {
        PlaceUser user = authorizationDAO.getUserByEmail(email.toLowerCase());
        authorizationDAO.deleteToken(user.getToken());
        saveRegistrationToken(user);
        return user;
    }

    @Override
    public void saveRegistrationToken(PlaceUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken tokens = new VerificationToken();
        tokens.setToken(token);
        authorizationDAO.newToken(tokens, user);
    }


    @Override
    public boolean confirmToken(String token) {
        VerificationToken tokens = authorizationDAO.findToken(token);
        if (tokens != null) {
            if (tokens.getDate().getTime() > Calendar.getInstance().getTime().getTime()) {
                PlaceUser user = tokens.getUser();
                user.setEnabled(true);
                authorizationDAO.deleteToken(user.getToken());
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean checkCredetials(String email, String userName) {
        return dao.checkCredentials(email, userName) == 0;
    }

    @Override
    public PlaceUser register(UserDTO blank, HttpServletRequest request) {
            PlaceUser user = new PlaceUser(blank);
            user.setPassword(passwordEncoder.encode(blank.getUserPass()));
            user.setEnabled(false);

            user = dao.registration(user);
            saveRegistrationToken(user);
            mailService.confirmEmailMessage(user, request);
            return user;
    }
}
