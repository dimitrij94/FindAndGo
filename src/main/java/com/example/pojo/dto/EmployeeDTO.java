package com.example.pojo.dto;

import java.util.List;

/**
 * Created by Dmitrij on 11.12.2015.
 */
public class EmployeeDTO {
    String email;
    String password;
    String name;
    String secondName;
    String description;
    int startAt;
    int endAt;
    List<Long>menuList;
    PhotoDTO photo;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartAt() {
        return startAt;
    }

    public void setStartAt(int startAt) {
        this.startAt = startAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }

    public List<Long> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Long> menuList) {
        this.menuList = menuList;
    }

    public PhotoDTO getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDTO photo) {
        this.photo = photo;
    }
}
