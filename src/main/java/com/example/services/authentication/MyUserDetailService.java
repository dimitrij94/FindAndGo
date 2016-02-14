package com.example.services.authentication;

import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.functional.user.GetUserByNameFunction;
import com.example.interfaces.Authenticational;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Dmitrij on 09.02.2016.
 */
@Component
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    AuthorizationDAO dao;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Authenticational user = null;
        name = name.toLowerCase();

        user = dao.getUserByEmail(name);
        if (user != null) return createUser(user);

        user =  dao.getOwnerByEmail(name);
        if(user!=null)return createUser(user);

        user = dao.getEmployeeByEmail(name);
        if(user!=null)return createUser(user);



        throw new BadCredentialsException("User does not exists");
    }

    private Callable<Authenticational> createTask(GetUserByNameFunction function, String name) {
        return () -> function.get(name);
    }

    private CustomUserDetails createUser(Authenticational user) {
        if (user == null) throw new BadCredentialsException("User does not exists");
        return new CustomUserDetails(user, getAuthorities(user.getAuthority()));
    }

    private List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }
}
