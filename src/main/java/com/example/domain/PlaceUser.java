package com.example.domain;


import com.example.domain.ratings.PlaceMenuRating;
import com.example.domain.ratings.PlaceRating;
import com.example.domain.addresses.UserAddress;
import com.example.domain.comment.PlaceComment;
import com.example.domain.photos.PlaceUserPhoto;
import com.example.domain.registration.Authorities;
import com.example.domain.registration.VerificationToken;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class PlaceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String userEmail;
    private String userPass;
    private boolean enabled;

    private String sex;
    private int age;
    private String name;
    private String sname;

    @OneToOne(mappedBy = "user")
    private UserAddress address;

    @OneToOne(mappedBy = "user")
    private VerificationToken tokens;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Authorities> authorities;

    @OneToMany(mappedBy = "user")
    private List<PlaceMenuRating> userMenuRatings;

    @OneToMany(mappedBy = "placeOwner")
    private List<Place> ownerPlaces;

    @OneToMany(mappedBy = "user")
    private List<PlaceRating> userPlacesRating;

    @ManyToMany(mappedBy = "users")
    private List<PlaceEvent> userEvents;

    @OneToMany(mappedBy = "user")
    private List<UserOrders> userOrderses;


    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "clientId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "placeId", referencedColumnName = "id"),
            name = "place_clients")
    private List<Place> userPlaces;

    @OneToMany(mappedBy = "user")
    private List<PlaceUserPhoto> photos;

    @OneToMany(mappedBy = "user")
    private List<PlaceComment> comments;

    @ManyToMany(mappedBy = "supporters")
    private List<PlaceComment>supported;


    public PlaceUser() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PlaceRating> getUserPlacesRating() {
        return userPlacesRating;
    }

    public void setUserPlacesRating(List<PlaceRating> userPlacesRating) {
        this.userPlacesRating = userPlacesRating;
    }

    public List<PlaceEvent> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(List<PlaceEvent> userEvents) {
        this.userEvents = userEvents;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPassword) {
        this.userPass = userPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public VerificationToken getToken() {
        return tokens;
    }

    public void setTokens(VerificationToken tokens) {
        this.tokens = tokens;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    public List<PlaceMenuRating> getUserMenuRatings() {
        return userMenuRatings;
    }

    public void setUserMenuRatings(List<PlaceMenuRating> user) {
        this.userMenuRatings = user;
    }

    public List<Place> getUserPlaces() {
        return userPlaces;
    }

    public void setUserPlaces(List<Place> userPlaces) {
        this.userPlaces = userPlaces;
    }

    public List<PlaceUserPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PlaceUserPhoto> photos) {
        this.photos = photos;
    }

    public List<PlaceComment> getComments() {
        return comments;
    }

    public void setComments(List<PlaceComment> comments) {
        this.comments = comments;
    }

    public List<PlaceComment> getSupported() {
        return supported;
    }

    public void setSupported(List<PlaceComment> supported) {
        this.supported = supported;
    }

    public List<Place> getOwnerPlaces() {
        return ownerPlaces;
    }

    public void setOwnerPlaces(List<Place> ownerPlaces) {
        this.ownerPlaces = ownerPlaces;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public VerificationToken getTokens() {
        return tokens;
    }

    public List<UserOrders> getUserOrderses() {
        return userOrderses;
    }

    public void setUserOrderses(List<UserOrders> userOrderses) {
        this.userOrderses = userOrderses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceUser user = (PlaceUser) o;

        return id.equals(user.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

