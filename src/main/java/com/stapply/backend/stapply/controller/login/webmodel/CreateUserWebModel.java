package com.stapply.backend.stapply.controller.login.webmodel;

import com.stapply.backend.stapply.models.User;

import javax.validation.constraints.Pattern;

public class CreateUserWebModel {
//    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String username;

//    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$")
    private String firstName;

//    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$")
    private String lastName;

//    @Pattern(regexp = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")
    private String email;

//    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
}
