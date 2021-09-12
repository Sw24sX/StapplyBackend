package com.stapply.backend.stapply.service.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GooglePlayCommentsParser {
    private final RestTemplate restTemplate;

    @Autowired
    public GooglePlayCommentsParser(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public
}
