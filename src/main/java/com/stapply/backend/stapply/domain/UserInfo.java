package com.stapply.backend.stapply.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "User_Info")
public class UserInfo extends BaseEntity {
    @Column(name = "email")
    private String email;

    @JsonBackReference("user-userInfo")
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
