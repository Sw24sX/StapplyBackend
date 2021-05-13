package com.stapply.backend.stapply.parser.scraper.detailed;

import com.stapply.backend.stapply.parser.scraper.AppImpl;

import java.util.ArrayList;
import java.util.List;

public class FullAppImplInfo extends AppImpl {

    public final String developer;
    public final Double score;
    public final List<String> images;
    public final String description;

    public FullAppImplInfo(AppImpl appImpl, String developer, Double score, String description, List<String> images) {
        super(appImpl.id, appImpl.name, appImpl.imageSrc);
        this.developer = developer;
        this.score = score;
        this.images = images;
        this.description = description;
    }

    public FullAppImplInfo(String id, String name, String imageSrc, String developer,
                           Double score, String description, List<String> images) {
        super(id, name, imageSrc);
        this.developer = developer;
        this.score = score;
        this.images = images;
        this.description = description;
    }

    public FullAppImplInfo(AppImpl appImpl) {
        super(appImpl.id, appImpl.name, appImpl.imageSrc);
        this.developer = null;
        this.score = null;
        this.images = new ArrayList<>();
        this.description = null;
    }

    public FullAppImplInfo() {
        super(null, null, null);
        this.developer = null;
        this.score = null;
        this.images = new ArrayList<>();
        this.description = null;
    }
}
