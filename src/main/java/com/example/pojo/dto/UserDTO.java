package com.example.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Dmitrij on 14.10.2015.
 */
@JsonIgnoreProperties({"sex","age","name","sname","image"})
public class UserDTO {

    String userEmail;
    String userName;
    String userPass;

    private String sex;

    private int age;

    private String name;

    private String sname;

    private CommonsMultipartFile image;

    public UserDTO() {
    }

    public UserDTO(String userEmail, String userName, String userPass, CommonsMultipartFile image) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPass = userPass;
        this.image = image;
    }

    public UserDTO(String userEmail, String userName, String userPass) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPass = userPass;
    }

    public UserDTO(MultipartHttpServletRequest request) {
        this.userEmail = request.getParameter("userEmail");
        this.userName = request.getParameter("userName");
        this.userPass = request.getParameter("userPass");
        this.sex = request.getParameter("sex");
        this.age = Integer.parseInt(request.getParameter("age"));
        this.name = request.getParameter("name");
        this.sname = request.getParameter("sname");
    }

    public UserDTO userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public UserDTO userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserDTO userPass(String userPass) {
        this.userPass = userPass;
        return this;
    }

    public UserDTO image(CommonsMultipartFile image) {
        this.image = image;
        return this;
    }


    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSname() {
        return sname;
    }

    public CommonsMultipartFile getImage() {
        return image;
    }
}
