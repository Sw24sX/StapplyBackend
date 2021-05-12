package com.stapply.backend.stapply.controller.search;

import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.mainscraper.SearchApp;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final StoreScraper googlePlayScraper;
    private final StoreScraper appStoreScraper;

    public SearchController() {
        this.appStoreScraper = ScraperFabric.AppStoreScraper();
        this.googlePlayScraper = ScraperFabric.GooglePlayScraper();
    }

    @GetMapping("/{query}")
    public ResponseEntity<Stream<SearchApp>> search(@PathVariable(name = "query")String query) {
        Stream<SearchApp> result;
        try {
            final var googlePlayApps = googlePlayScraper.search(query);
            final var appStoreApps = appStoreScraper.search(query);
            AtomicInteger id = new AtomicInteger(0);
            result = googlePlayApps.stream().map(x -> {
                var app = new SearchApp();
                app.setName(x.name);
                app.setAvatarSrc(x.imageSrc);
                var developer = x.otherParameters.get("developer");
                app.setDeveloper(developer);
                var srcGoogle = String.format("https://play.google.com/store/apps/details?id=%s&hl=ru&gl=US",
                        x.id);
                app.setLinkGooglePlay(srcGoogle);
                app.setId(id.addAndGet(1));
                return app;
            });
        } catch (IOException | URISyntaxException exception) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
