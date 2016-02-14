package com.example.domain.registration;

import com.example.domain.users.PlaceUser;
import com.example.services.registration.RegistrationService;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Dmitrij on 22.09.2015.
 */
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private  String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private   PlaceUser user;

    @Column(name = "expiry_date")
    private   Date date;

    public VerificationToken() {
        this.date= RegistrationService.calculateExpiryDate(240);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PlaceUser getUser() {
        return user;
    }

    public void setUser(PlaceUser user) {
        this.user = user;
    }
}
