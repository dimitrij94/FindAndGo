package com.example.services.authorization;


import com.example.domain.PlaceUser;
import com.example.domain.registration.Authorities;
import com.example.dao.IDBBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitrij on 25.09.2015.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IDBBean dao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PlaceUser user = dao.getUserByName(s);
        if (!(user == null)) {
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new User(user.getUserEmail(),
                    user.getUserPass().toLowerCase(), user.isEnabled(),
                    accountNonExpired, credentialsNonExpired, accountNonLocked,
                    getAuthorities(user));
        }
        throw new UsernameNotFoundException("No user found with username: " + s);
    }

    private List<GrantedAuthority> getAuthorities(PlaceUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isEnabled()) {
            List<Authorities> authoritiesList = user.getAuthority();
            for (Authorities a : authoritiesList) {
                authorities.add(new SimpleGrantedAuthority(a.getAuthority()));
            }
        }
        return authorities;
    }
}


