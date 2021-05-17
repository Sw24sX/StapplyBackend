package com.stapply.backend.stapply.service.search.servicemodel;

public class AppMarketId {
    private final String googlePlay;
    private final String appStore;
    private final String appGallery;

    public AppMarketId(String googlePlay, String appStore, String appGallery) {
        this.googlePlay = googlePlay;
        this.appStore = appStore;
        this.appGallery = appGallery;
    }

    public String getGooglePlay() {
        return googlePlay;
    }

    public String getAppStore() {
        return appStore;
    }

    public String getAppGallery() {
        return appGallery;
    }
}
