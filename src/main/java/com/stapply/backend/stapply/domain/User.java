package com.stapply.backend.stapply.domain;

import com.stapply.backend.stapply.converter.RoleCodeConverter;
import com.stapply.backend.stapply.enums.Role;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "User")
public class User extends BaseEntity{
    @NotNull
    @Column(name = "login")
    private String login;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "role_code", nullable = false)
    @Convert(converter = RoleCodeConverter.class)
    private Role role;

    @ManyToMany(mappedBy = "users")
    private List<App> apps;
}
