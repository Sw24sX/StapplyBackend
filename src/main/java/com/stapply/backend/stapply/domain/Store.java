package com.stapply.backend.stapply.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "Store")
public class Store extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "store")
    private List<App> apps;
}
