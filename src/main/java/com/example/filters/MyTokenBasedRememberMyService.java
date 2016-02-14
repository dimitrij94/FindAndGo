package com.example.filters;

import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dmitrij on 17.01.2016.
 */
public class MyTokenBasedRememberMyService extends TokenBasedRememberMeServices {
    @Override
    protected String extractRememberMeCookie(HttpServletRequest request) {
        return request.getHeader(super.getCookieName());
    }
}
