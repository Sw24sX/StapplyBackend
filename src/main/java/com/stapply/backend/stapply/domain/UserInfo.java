package com.stapply.backend.stapply.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "User_Info")
public class UserInfo extends BaseEntity {
    @Column(name = "email")
    private String email;
}
