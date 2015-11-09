package com.example.services.mail;

import com.example.domain.PlaceUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dmitrij on 20.10.2015.
 */
public interface MailService {

    void confirmEmailMessage(PlaceUser user, HttpServletRequest request);
}
