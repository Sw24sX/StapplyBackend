package com.stapply.backend.stapply.models;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "APPMAIN")
public class AppMain {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "this is id in database")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DEVELOPER")
    private String developer;

    @Column(name = "AVATAR_SRC")
    private String avatarSrc;

    @Column(name = "IMAGE_SRC")
    private String imageSrc;

    @Column(name = "GOOGLE_PLAY_SPC")
    private String googlePlaySrc;

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

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
