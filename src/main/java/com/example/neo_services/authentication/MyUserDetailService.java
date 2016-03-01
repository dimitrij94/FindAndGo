package com.example.neo_services.authentication;

import com.example.graph.Person;
import com.example.graph_repositories.PersonRepository;
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

/**
 * Created by Dmitrij on 09.02.2016.
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    private PersonRepository personRepository;

    @Autowired
    public MyUserDetailService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Person person = personRepository.findByUserName(name);
        return createUser(person);
    }

    private CustomUserDetails createUser(Person user) {
        if (user == null) throw new BadCredentialsException("User does not exists");
        return new CustomUserDetails(user, getAuthorities(user.getAuthority()));
    }

    private List<GrantedAuthority> getAuthorities(String authority) {
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }
}
