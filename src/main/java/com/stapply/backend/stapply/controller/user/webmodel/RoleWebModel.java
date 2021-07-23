package com.stapply.backend.stapply.controller.user.webmodel;

import com.stapply.backend.stapply.domain.Role;

public class RoleWebModel {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RoleWebModel fromRole(Role role) {
        var result = new RoleWebModel();
        result.id = role.getId();
        result.name = role.getName();

        return result;
    }
}
