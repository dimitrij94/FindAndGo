package com.example.services.registration;


import com.example.domain.users.PlaceUser;
import com.example.pojo.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dmitrij on 23.09.2015.
 */

public interface RegistrationService {

    PlaceUser resendRegistrationToken(String token);

    void saveRegistrationToken(PlaceUser user);

    boolean confirmToken(String token);

    boolean checkCredetials(String email, String userName);

    PlaceUser register(UserDTO blank, HttpServletRequest request);

}
