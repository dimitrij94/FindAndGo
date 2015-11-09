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

/**
 * Created by Dmitrij on 21.09.2015.
 */
@Component
public class MySimpleUrlAuthenticationSuccessHendler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        for (GrantedAuthority g:authorities){
            if(g.getAuthority()=="ROLE_OWNER"){
                sendRedirect(httpServletRequest, httpServletResponse, "/owner/profile");
            }
            if(g.getAuthority()=="ROLE_USER"){
                sendRedirect(httpServletRequest,httpServletResponse,"/user");
            }
        }

    }
    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
        if(!response.isCommitted()){
            new DefaultRedirectStrategy().sendRedirect(request,response,url);
        }
    }
}
