package com.example.services.authorization;

import com.example.interfaces.Authenticational;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Dmitrij on 03.11.2015.
 */
public class CustomUserDetails extends User {

    private Long id;
    private String userEmail;


    public CustomUserDetails(Authenticational user,
                             Collection<? extends GrantedAuthority> authorities) {
        super(
                user.getName(),
                user.getPassword().toLowerCase(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);
        upadateValues(user);
    }

    private void upadateValues(Authenticational user) {
        this.id = user.getId();
        this.userEmail = user.getEmail();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
