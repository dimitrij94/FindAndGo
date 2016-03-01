package com.example.neo_services.authentication;

import com.example.graph.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by Dmitrij on 03.11.2015.
 */
public class CustomUserDetails extends User implements UserDetails {

    private Long id;
    private String email;

    public CustomUserDetails(Person user,
                             Collection<? extends GrantedAuthority> authorities) {
        super(
                user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);

        upadateValues(user);
    }

    private void upadateValues(Person user) {
        this.id = user.getId();
        this.email = user.getEmail();
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

    public String getEmail() {
        return email;
    }
}
