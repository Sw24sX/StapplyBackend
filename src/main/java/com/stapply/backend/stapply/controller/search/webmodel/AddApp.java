package com.stapply.backend.stapply.controller.search.webmodel;

import com.stapply.backend.stapply.service.appmain.servicemodels.AppLinks;

public class AddApp {
    private String name;
    private String googlePlayAppLink;
    private String appStoreAppLik;
    private String appGalleryAppLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGooglePlayAppLink() {
        return googlePlayAppLink;
    }

    public void setGooglePlayAppLink(String googlePlayAppLink) {
        this.googlePlayAppLink = googlePlayAppLink;
    }

    public String getAppStoreAppLik() {
        return appStoreAppLik;
    }

    public void setAppStoreAppLik(String appStoreAppLik) {
        this.appStoreAppLik = appStoreAppLik;
    }

    public String getAppGalleryAppLink() {
        return appGalleryAppLink;
    }

    public void setAppGalleryAppLink(String appGalleryAppLink) {
        this.appGalleryAppLink = appGalleryAppLink;
    }

    public static boolean isValid(AddApp app) {
        return (app.googlePlayAppLink != null || app.appGalleryAppLink != null
                || app.appStoreAppLik != null) && app.name != null;
    }

    public static AppLinks toAppLinks(AddApp app) {
        var res = new AppLinks();
        res.setName(app.getName());
        res.setAppGalleryAppLink(app.getAppGalleryAppLink());
        res.setGooglePlayAppLink(app.getGooglePlayAppLink());
        res.setAppStoreAppLik(app.getAppStoreAppLik());
        return res;
    }
}
