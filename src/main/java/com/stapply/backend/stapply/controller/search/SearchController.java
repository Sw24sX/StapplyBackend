package com.stapply.backend.stapply.controller.search;

import com.stapply.backend.stapply.controller.search.webmodel.SearchApp;
import com.stapply.backend.stapply.parser.UserUrlParser;
import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import com.stapply.backend.stapply.service.appmain.AppMainService;
import com.stapply.backend.stapply.service.search.SearchService;
import com.stapply.backend.stapply.service.search.SearchServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final StoreScraper googlePlayScraper;
    private final StoreScraper appStoreScraper;
    private final AppMainService appService;
    private final SearchService searchService;

    @Autowired
    public SearchController(AppMainService appService, SearchService searchService) {
        this.appStoreScraper = ScraperFabric.AppStoreScraper();
        this.googlePlayScraper = ScraperFabric.GooglePlayScraper();
        this.appService = appService;
        this.searchService = searchService;
    }

    @GetMapping("/{query}")
    @ApiOperation(value = "Search on Google play and AppStore. Uses the Levenshtein distance")
    public ResponseEntity<Stream<SearchApp>> search(
            @PathVariable(name = "query")
            @ApiParam(value = "User's search query")
            String query,

            @RequestParam(defaultValue = "3")
            @ApiParam(value = "Length differences in app names", format = "integer")
            Integer accuracy) {
        final var app = searchService.search(query, accuracy);
        if(app == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        final var result = app.stream().map(SearchApp::fromAppMarketSearch);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
