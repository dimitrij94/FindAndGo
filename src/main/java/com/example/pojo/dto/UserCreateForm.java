package com.example.pojo.dto;

import com.example.domain.validation.annotations.UserPasswordsEqual;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by Dmitrij on 14.10.2015.
 */

@UserPasswordsEqual
public class UserCreateForm {

    @Email(message = "email is not valid")
    @NotBlank(message = "email must not be blank")
    String userEmail;

    @NotBlank
    @Pattern(message = "Name is not valid", regexp = "[a-zA-Z0-9]{3,20}")
    String userName;

    @Pattern(message = "Password is not valid", regexp = "[a-zA-Z0-9]{3,20}")
    @NotBlank
    String userPass;

    AddressDTO address;

    String userPassConf;

    private String sex;

    private int age;

    private String name;

    private String sname;

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
}
