package com.stapply.backend.stapply.parser.scraper.search;

import com.stapply.backend.stapply.parser.scraper.AppImpl;

import java.util.HashMap;
import java.util.Map;

public class SearchAppImplInfo extends AppImpl {

    public final HashMap<String, String> otherParameters;

    public SearchAppImplInfo(String image, String name, String id) {
        super(id, name, image);
        this.otherParameters = new HashMap<>();
    }
}
