package com.stapply.backend.stapply.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app")
@Data
public class AppMain {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @ApiModelProperty(notes = "this is id in database")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "developer")
    private String developer;

    @Column(name = "avatar_src")
//    @Lob
    private String avatarSrc;

    @Column(name = "google_play_id")
    private String googlePlayId;

    @Column(name = "app_store_id")
    private String appStoreId;

    @Column(name = "app_gallery_id")
    private String appGalleryId;

//    @Column(name = "img_src_list")
//    @ElementCollection
//    private List<String> imageSrcList = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @Column(name = "score_google_play")
    private Double scoreGooglePlay;

    @Column(name = "score_app_store")
    private Double scoreAppStore;

    @Column(name = "score_app_gallery")
    private Double scoreAppGallery;
}
