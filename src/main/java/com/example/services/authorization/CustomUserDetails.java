package com.example.services.authorization;

import com.example.domain.PlaceUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Dmitrij on 03.11.2015.
 */
public class CustomUserDetails extends User {
    private PlaceUser user;

    public CustomUserDetails(PlaceUser user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.user=user;
    }

    public CustomUserDetails(PlaceUser user, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user=user;
    }

    public PlaceUser getUser() {
        return user;
    }
}
