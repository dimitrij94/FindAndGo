package com.example.services.authentication;

import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.domain.employee.PlaceEmployee;
import com.example.domain.owner.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.functional.user.GetUserByNameFunction;
import com.example.interfaces.Authenticational;
import com.example.services.MyExecutorService;
import com.example.services.authentication.employee.CustomEmployeeDetails;
import com.example.services.authentication.owner.CustomOwnerDetails;
import com.example.services.authentication.user.CustomUserDetails;
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
    MyExecutorService executorService;

    @Autowired
    AuthorizationDAO dao;


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        PlaceUser user = dao.getUserByEmail(name);
        if (user != null) return new CustomUserDetails(user, getAuthorities(user.getAuthority()));

        PlaceOwner owner = dao.getOwnerByEmail(name);
        if (owner != null) return new CustomOwnerDetails(owner, getAuthorities(owner.getAuthority()));

        PlaceEmployee employee = dao.getEmployeeByEmail(name);
        if (employee != null) return new CustomEmployeeDetails(employee, getAuthorities(employee.getAuthority()));

        throw new BadCredentialsException("User does not exist");
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
