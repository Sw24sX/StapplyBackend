package com.stapply.backend.stapply.parser.scraper.review;

import com.stapply.backend.stapply.parser.scraper.AppImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface IReviewScraper {
    List<Review> getComments(AppImpl appImpl, Integer count) throws IOException, URISyntaxException, InterruptedException;
}
