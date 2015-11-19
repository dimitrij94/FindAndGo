package com.example.domain.registration;

import com.example.domain.PlaceUser;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Dmitrij on 21.09.2015.
 */
@Entity
@Table(name = "authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String authority;
    @ManyToMany(mappedBy = "authority")
    List<PlaceUser> user;

    public Authorities(){}

    public Authorities(String role){
        this.authority=role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlaceUser> getUser() {
        return user;
    }

    public void setUser(List<PlaceUser> user) {
        this.user = user;
    }

    public enum Roles{ROLE_USER,ROLE_OWNER}
}
