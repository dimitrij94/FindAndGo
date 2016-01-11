package com.example.services.authorization;


import com.example.constants.UserClasses;
import com.example.dao.IDBBean;
import com.example.functional.user.GetUserByNameFunction;
import com.example.interfaces.Authenticational;
import com.example.services.MyExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.el.LambdaExpression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IDBBean dao;

    @Autowired
    MyExecutorService service;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        CustomUserDetails userDetails = null;
        List<Callable<CustomUserDetails>> tasks = new ArrayList<>(UserClasses.values().length);

        tasks.add(() -> getUser(dao::getUserByName, userName));
        tasks.add(() -> getUser(dao::getOwnerByName, userName));
        tasks.add(() -> getUser(dao::getEmployeeByName, userName));

        try {
            userDetails = service.getExecutor().invokeAny(tasks);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (userDetails != null) return userDetails;

        throw new BadCredentialsException("Невірний логін/пароль");
    }

    private CustomUserDetails createUser(Authenticational user) {
        return new CustomUserDetails(user, getAuthorities(user.getAuthority()));
    }

    private List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    private CustomUserDetails getUser(GetUserByNameFunction function, String name) {
        return createUser(function.get(name));
    }

}


