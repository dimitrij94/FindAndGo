package com.example.services.authentication;

import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.functional.user.GetUserByNameFunction;
import com.example.interfaces.Authenticational;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dmitrij on 16.01.2016.
 */
@Service
public abstract class CustomUserDetailService implements UserDetailsService{

    @Autowired
    protected AuthorizationDAO dao;

    protected CustomUserDetails getUser(GetUserByNameFunction function, String name) {
        name = name.toLowerCase();
        return createUser(function.get(name));
    }

    private CustomUserDetails createUser(Authenticational user) {
        return new CustomUserDetails(user, getAuthorities(user.getAuthority()));
    }

    private List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }
}
