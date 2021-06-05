package com.stapply.backend.stapply.models;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
public class Market {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "this is id in database")
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
