package com.stapply.backend.stapply.controller.user.webmodel;

import com.stapply.backend.stapply.models.Role;
import com.stapply.backend.stapply.models.User;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

public class UserWebModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleWebModel> roles;

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

    public List<RoleWebModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleWebModel> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static UserWebModel fromUser(User user) {
        var result = new UserWebModel();
        result.email = user.getEmail();
        result.firstName = user.getFirstName();
        result.lastName = user.getLastName();
        result.username = user.getUsername();
        result.roles = user.getRoles().stream().map(RoleWebModel::fromRole).collect(Collectors.toList());
        result.id = user.getId();

        return result;
    }
}
