package com.example.services.authentication.user;

import com.example.interfaces.Authenticational;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by Dmitrij on 03.11.2015.
 */
public class CustomUserDetails extends User implements UserDetails {

    private Long id;


    public CustomUserDetails(Authenticational user,
                             Collection<? extends GrantedAuthority> authorities) {
        super(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);

        upadateValues(user);
    }

    private void upadateValues(Authenticational user) {
        this.id = user.getId();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
