package com.example.services.registration;


import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitrij on 23.09.2015.
 */

public interface RegistrationService {

    PlaceUser resendRegistrationToken(String token);

    void saveRegistrationToken(PlaceUser user);

    boolean confirmToken(String token);

    boolean checkCredetials(String email, String userName);

    PlaceUser register(UserDTO blank, HttpServletRequest request);

    static Date calculateExpiryDate(int expiryMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryMinutes);
        return new Date(cal.getTime().getTime());
    }
}
