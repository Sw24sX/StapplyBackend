package com.stapply.backend.stapply.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "APPMAIN")
@Data
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
}
