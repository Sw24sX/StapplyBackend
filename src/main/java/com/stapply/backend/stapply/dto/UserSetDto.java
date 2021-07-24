package com.stapply.backend.stapply.dto;

import com.stapply.backend.stapply.converter.RoleCodeConverter;
import com.stapply.backend.stapply.domain.App;
import com.stapply.backend.stapply.domain.User;
import com.stapply.backend.stapply.domain.UserInfo;
import com.stapply.backend.stapply.enums.Role;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
public class UserSetDto {
    private String login;
    private String name;
    private String surname;
    private String password;
    private Role role;

    public User toUser() {
        User user = new User();
        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
