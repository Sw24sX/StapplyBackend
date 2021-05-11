package com.stapply.backend.stapply.models;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Column(name = "GOOGLE_PLAY_SPC")
    private String googlePlaySrc;

    @Column(name = "APP_STORE_SRC")
    private String appStoreSrc;

    @Column(name = "IMG_SRC_LIST")
    @ElementCollection
    private List<String> imageSrcList;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SCORE")
    @Value("0.0")
    private Double score;

    public List<String> getImageSrcList() {
        return imageSrcList;
    }

    public void setImageSrcList(List<String> imageSrcList) {
        this.imageSrcList = imageSrcList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getAppStoreSrc() {
        return appStoreSrc;
    }

    public void setAppStoreSrc(String appStoreSrc) {
        this.appStoreSrc = appStoreSrc;
    }

    public String getGooglePlaySrc() {
        return googlePlaySrc;
    }

    public void setGooglePlaySrc(String googlePlaySrc) {
        this.googlePlaySrc = googlePlaySrc;
    }

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
}
