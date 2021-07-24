package com.stapply.backend.stapply.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "App")
public class App extends BaseEntity{
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    //TODO
    @Column(name = "info_id")
    private Long infoID;

    @JsonManagedReference("users-apps")
    @ManyToMany
    @JoinTable(name = "User_App",
            joinColumns = @JoinColumn(name = "app_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "app")
    private List<Comment> comments;
}
