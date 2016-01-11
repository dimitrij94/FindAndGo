package com.example.pojo.dto;

import com.example.domain.users.PlaceUser;

/**
 * Created by Dmitrij on 14.10.2015.
 */

public class UserCreateForm {

    String userEmail;
    String userName;
    String userPass;
    AddressDTO address;
    String userPassConf;

    private String sex;

    private int age;

    private String name;

    private String sname;

    private PhotoDTO photo;

    public UserCreateForm() {
        photo = new PhotoDTO();
    }

    public UserCreateForm(PlaceUser user) {
        this.userEmail=user.getEmail();
        this.userName=user.getUserName();
        this.name=user.getName();
        this.sname=user.getSname();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserPassConf() {
        return userPassConf;
    }

    public void setUserPassConf(String userPassConf) {
        this.userPassConf = userPassConf;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
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

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }
}
