package com.example.domain.registration;

import com.example.domain.owner.PlaceOwner;
import com.example.services.registration.RegistrationService;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Dmitrij on 08.02.2016.
 */
@Entity
public class OwnerVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String token;

    @OneToOne
    @JoinColumn(name = "owner")
    PlaceOwner owner;

    @Column(name = "expiry_date")
    private Date date;

    public OwnerVerificationToken() {
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

    public PlaceOwner getOwner() {
        return owner;
    }

    public void setOwner(PlaceOwner owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
