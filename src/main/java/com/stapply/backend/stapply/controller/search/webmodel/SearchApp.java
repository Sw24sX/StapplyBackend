package com.stapply.backend.stapply.controller.search.webmodel;

import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import com.stapply.backend.stapply.service.search.servicemodel.AppMarketId;
import com.stapply.backend.stapply.service.search.servicemodel.AppMarketSearch;
import lombok.var;

import java.util.Arrays;

public class SearchApp {
    private Long id;
    private String name;
    private String developer;
    private String avatarSrc;
    private String linkAppStore;
    private String linkAppGallery;
    private String linkGooglePlay;
    private boolean isTracking = false;

    public Boolean getTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
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

    public String getLinkAppStore() {
        return linkAppStore;
    }

    public void setLinkAppStore(String linkAppStore) {
        this.linkAppStore = linkAppStore;
    }

    public String getLinkAppGallery() {
        return linkAppGallery;
    }

    public void setLinkAppGallery(String linkAppGallery) {
        this.linkAppGallery = linkAppGallery;
    }

    public String getLinkGooglePlay() {
        return linkGooglePlay;
    }

    public void setLinkGooglePlay(String linkGooglePlay) {
        this.linkGooglePlay = linkGooglePlay;
    }

    public static SearchApp fromAppMarketSearch(AppMarketSearch app) {
        var result = new SearchApp();
        result.setId(app.getId());
        result.setName(app.getName());
        result.setDeveloper(app.getDeveloper());
        result.setAvatarSrc(app.getAvatarSrc());
        result.setLinkAppGallery(app.getLinkAppGallery());
        result.setLinkGooglePlay(app.getLinkGooglePlay());
        result.setLinkAppStore(app.getLinkAppStore());
        result.setTracking(app.getTracking());
        return result;
    }
}
