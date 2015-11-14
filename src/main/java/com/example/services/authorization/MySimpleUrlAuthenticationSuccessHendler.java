package com.example.services.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Dmitrij on 21.09.2015.
 */
@Component
public class MySimpleUrlAuthenticationSuccessHendler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        if(httpServletRequest.getContextPath().equals("/login")){
            sendRedirect(httpServletRequest, httpServletResponse, "/user/profile");
        }
        else{
            sendRedirect(httpServletRequest, httpServletResponse,httpServletRequest.getContextPath());
        }

    }
    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        if(!response.isCommitted()){
            new DefaultRedirectStrategy().sendRedirect(request,response,url);
        }
    }
}
