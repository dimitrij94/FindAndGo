package com.example.services.authorization;


import com.example.domain.users.PlaceOwner;
import com.example.domain.users.PlaceUser;
import com.example.dao.IDBBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dmitrij on 25.09.2015.
 */
@Service
@Scope(value = "prototype")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IDBBean dao;

    private PlaceUser user = null;
    private PlaceOwner owner = null;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Thread findUser = new Thread(() -> {
            setUser(dao.getUserByName(s));
        });
        Thread findOwner = new Thread(() -> {
            setOwner(dao.getOwnerByName(s));
        });

        findUser.start();
        findOwner.start();


        try {
            findUser.join();
            if (this.user != null) {
                return createUser(this.user.getUserEmail(),
                        this.user.getUserPass(),
                        this.user.isEnabled(),
                        getAuthorities("ROLE_USER"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            findOwner.join();
            if (owner != null) {
                return createUser(this.owner.getEmail(),
                        this.owner.getPassword(),
                        this.owner.isEnabled(),
                        getAuthorities("ROLE_OWNER"));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new BadCredentialsException("Невірний логін/пароль");
    }

    private User createUser(String email, String password, boolean enabled, List<GrantedAuthority> authorities) {
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(email, password.toLowerCase(), enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);
    }

    private void setUser(PlaceUser user) {
        this.user = user;
    }

    public void setOwner(PlaceOwner owner) {
        this.owner = owner;
    }

    private List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }

    /*
     Thread userThread = new Thread(() -> {
            try {
                findUser.join();
                if (this.user != null) {
                    fUser = createUser(this.user.getUserEmail(),
                            this.user.getUserPass(),
                            this.user.isEnabled(),
                            getAuthorities("ROLE_USER"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread ownerThread = new Thread(() -> {
            try {
                findOwner.join();
                if (owner != null) {
                    fUser = createUser(owner.getEmail(),
                            owner.getPassword(),
                            owner.isEnabled(),
                            getAuthorities("ROLE_OWNER"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        userThread.start();
        ownerThread.start();

        while (fUser == null) {
            if (!(findUser.isAlive() && findOwner.isAlive())) {
                if (this.user == null && owner == null)
                    throw new BadCredentialsException("Невірний логін/пароль");
            }
            Thread.yield();
        }
        return fUser;
     */
}


