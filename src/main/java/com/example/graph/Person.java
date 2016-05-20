package com.example.graph;

import com.example.interfaces.PhotoContainable;
import com.example.json_views.UserJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by Dmitrij on 24.02.2016.
 */
@NodeEntity
public class Person implements PhotoContainable {
    @GraphId
    private Long id;

    
    @JsonView(UserJsonView.class)
    @Indexed
    protected String userName;
    protected String email;
    protected String password;
    protected boolean enabled;
    protected String authority;

    public Person(Long id, String userName, String email, String password, boolean enabled, String authority) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
    }

    public Person() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!id.equals(person.id)) return false;
        if (!userName.equals(person.userName)) return false;
        return email.equals(person.email);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
