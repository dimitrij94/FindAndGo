package com.example.domain.registration;

import com.example.domain.users.PlaceUser;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dmitrij on 22.09.2015.
 */
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String token;

    @Column(name = "expiry_date")
    Date date;

    @OneToOne
    @JoinColumn(name = "user_id")
    PlaceUser user;

    public VerificationToken() {
        this.date=calculateExpiryDate(240);
    }

    private Date calculateExpiryDate(int expiryMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryMinutes);
        return new Date(cal.getTime().getTime());
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
