package com.example.services.authentication.owner;

import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.domain.owner.PlaceOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dmitrij on 16.01.2016.
 */
public class OwnerDetailsService implements UserDetailsService {


    @Autowired
    protected AuthorizationDAO dao;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        userEmail = userEmail.toLowerCase();
        PlaceOwner owner= dao.getOwnerByEmail(userEmail);
        if (owner == null) return null;
        return new CustomOwnerDetails(owner, getAuthorities(owner.getAuthority()));
    }

    protected List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }
}
