package com.stapply.backend.stapply.controller.search;

import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.mainscraper.SearchApp;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<List<SearchApp>> search(@PathVariable(name = "query")String query, @RequestParam(defaultValue = "3")String accuracy) {
        int acc;
        try {
            acc = Integer.parseInt(accuracy);
        }
        catch (NumberFormatException ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AtomicInteger id = new AtomicInteger(0);
        List<SearchApp> result;
        try {
            final var googlePlayApps = googlePlayScraper.search(query);
            List<SearchApp> list = new ArrayList<>();
            long limit = 10;
            for (SearchAppImplInfo x : googlePlayApps) {
                SearchApp searchApp = SearchApp.fromGoogleApp(x, id.addAndGet(1));
                if (limit-- == 0) break;
                list.add(searchApp);
            }
            result = list;


        } catch (IOException | URISyntaxException exception) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }

        try {
            final var appStoreApps = appStoreScraper.search(query);
            for (var app : appStoreApps) {
                var mostAccuracy = Integer.MAX_VALUE;
                SearchApp appMostAccuracy = null;
                for(var gp : result){
                    if(gp.getLinkAppStore() != null)
                        continue;

                    var distance = gp.calculate(app.name);
                    if(distance <= acc && distance < mostAccuracy) {
                        mostAccuracy = distance;
                        appMostAccuracy = gp;
                    }
                }
                var appStoreSrc = "https://apps.apple.com/ru/app/" + app.id;
                if(appMostAccuracy == null) {
                    var asApp = new SearchApp();
                    asApp.setId(id.addAndGet(1));
                    asApp.setLinkAppStore(appStoreSrc);
                    asApp.setName(app.name);
                    asApp.setAvatarSrc(app.imageSrc);
                    result.add(asApp);
                }
                else {
                    appMostAccuracy.setLinkAppStore(appStoreSrc);
                }
            }
        } catch (IOException | URISyntaxException exception) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
