package com.example.services.authentication.user;


import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.domain.users.PlaceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    protected AuthorizationDAO dao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            userName = userName.toLowerCase();
            PlaceUser user = dao.getUserByEmail(userName);
            if (user == null) throw new BadCredentialsException("User does not exists");
            return new CustomUserDetails(user, getAuthorities(user.getAuthority()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    protected List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

}


