package com.stapply.backend.stapply.parser.scraper;

public abstract class AppImpl {
    public final String id;
    public final String name;
    public final String imageSrc;

    public AppImpl(String id, String name, String imageSrc) {
        this.id = id;
        this.name = name;
        this.imageSrc = imageSrc;
    }
}
