package com.example.services.authentication.employee;

import com.example.dao.authoriztion.AuthorizationDAO;
import com.example.domain.employee.PlaceEmployee;
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

public class EmployeeDetailServiceImpl implements UserDetailsService {

    @Autowired
    protected AuthorizationDAO dao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        userName = userName.toLowerCase();
        PlaceEmployee employee = dao.getEmployeeByEmail(userName);
        return employee == null ? null : createUser(employee);
    }

    private CustomEmployeeDetails createUser(PlaceEmployee employee) {
        return new CustomEmployeeDetails(employee, getAuthorities(employee.getAuthority()));
    }

    protected List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }
}