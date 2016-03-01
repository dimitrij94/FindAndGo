package com.example.graph;

import com.example.interfaces.PhotoCotainable;
import com.example.json_views.UserJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by Dmitrij on 24.02.2016.
 */
@NodeEntity
public class Person implements PhotoCotainable{
    @GraphId
    private Long id;

    
    @JsonView(UserJsonView.class)
    @Indexed
    private String userName;
    private String email;
    private String password;
    private boolean enabled;
    private String authority;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
