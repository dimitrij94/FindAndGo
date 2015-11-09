package com.example.domain.registration;

import com.example.domain.PlaceUser;

import javax.persistence.*;

/**
 * Created by Dmitrij on 21.09.2015.
 */
@Entity
@Table(name = "user_authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String authority;
    @ManyToOne
    @JoinColumn(name = "user_id")
    PlaceUser user;

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

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }

    public enum Roles{ROLE_USER,ROLE_OWNER}
}
