package com.example.services.authorization;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by Dmitrij on 16.01.2016.
 */

public class EmployeeDetailServiceImpl extends CustomUserDetailService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return super.getUser(dao::getEmployeeByEmail, email);
    }
}
