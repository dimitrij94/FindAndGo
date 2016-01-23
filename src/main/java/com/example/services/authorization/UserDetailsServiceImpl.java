package com.example.services.authorization;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl extends CustomUserDetailService{


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return super.getUser(dao::getUserByEmail, userName);
    }
}


