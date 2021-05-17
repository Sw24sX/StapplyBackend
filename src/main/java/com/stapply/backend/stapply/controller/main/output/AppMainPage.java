package com.stapply.backend.stapply.controller.main.output;

import com.stapply.backend.stapply.models.AppMain;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

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
        var images =  app.getImageSrcList();
        this.imageSrc = !images.isEmpty() ? images.get(0) : null;
    }
}
