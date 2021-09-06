package com.stapply.backend.stapply.controller.main.webmodel;

import com.stapply.backend.stapply.domain.AppMain;

public class AppMainPage {
    public final Long id;
    public final String name;
    public final String developer;
    public final String avatarSrc;
    public final String imageSrc;

    public AppMainPage(AppMain app) {
        this.id = app.getId();
        this.name = app.getName();
        this.developer = app.getDeveloper();
        this.avatarSrc = app.getAvatarSrc();
//        var images =  app.getImageSrcList();
//        this.imageSrc = !images.isEmpty() ? images.get(0) : null;
        this.imageSrc = "";
    }
}
