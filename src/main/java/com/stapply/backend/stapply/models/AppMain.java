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

    @Column(name = "AVATAR_SRC", length = 500)
    @Lob
    private String avatarSrc;

    @Column(name = "GOOGLE_PLAY_ID")
    private String googlePlayId;

    @Column(name = "APP_STORE_ID")
    private String appStoreId;

    @Column(name = "APP_GALLERY_ID")
    private String appGalleryId;

    @Column(name = "IMG_SRC_LIST", length = 500)
    @ElementCollection
    private List<String> imageSrcList = new ArrayList<>();

    @Column(name = "DESCRIPTION", length = 1500)
    private String description;

    @Column(name = "SCORE_GOOGLE_PLAY")
    @Value("0.0")
    private Double scoreGooglePlay;

    @Column(name = "SCORE_APP_STORE")
    @Value("0.0")
    private Double scoreAppStore;

    @Column(name = "SCORE_APP_GALLERY")
    @Value("0.0")
    private Double scoreAppGallery;

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

    public Double getScoreGooglePlay() {
        return scoreGooglePlay;
    }

    public void setScoreGooglePlay(Double scoreGooglePlay) {
        this.scoreGooglePlay = scoreGooglePlay;
    }

    public Double getScoreAppStore() {
        return scoreAppStore;
    }

    public void setScoreAppStore(Double scoreAppStore) {
        this.scoreAppStore = scoreAppStore;
    }

    public Double getScoreAppGallery() {
        return scoreAppGallery;
    }

    public void setScoreAppGallery(Double scoreAppGallery) {
        this.scoreAppGallery = scoreAppGallery;
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

    public String getGooglePlayId() {
        return googlePlayId;
    }

    public void setGooglePlayId(String googlePlayId) {
        this.googlePlayId = googlePlayId;
    }

    public String getAppStoreId() {
        return appStoreId;
    }

    public void setAppStoreId(String appStoreId) {
        this.appStoreId = appStoreId;
    }
}
