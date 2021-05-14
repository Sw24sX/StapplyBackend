package com.stapply.backend.stapply.controller.search;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.parser.UserUrlParser;
import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.parser.scraper.detailed.FullAppImplInfo;
import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import com.stapply.backend.stapply.service.AppMainService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final StoreScraper googlePlayScraper;
    private final StoreScraper appStoreScraper;
    private final AppMainService appService;

    @Autowired
    public SearchController(AppMainService appService) {
        this.appStoreScraper = ScraperFabric.AppStoreScraper();
        this.googlePlayScraper = ScraperFabric.GooglePlayScraper();
        this.appService = appService;
    }

    @ApiOperation(value = "Delete app by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppMain(@PathVariable(name = "id")Long id) {
        final var result = appService.delete(id);
        return result ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{query}")
    @ApiOperation(value = "Search on Google play and AppStore. Uses the Levenshtein distance")
    public ResponseEntity<List<SearchApp>> search(
            @PathVariable(name = "query")
            @ApiParam(value = "User's search query")
            String query,

            @RequestParam(defaultValue = "3")
            @ApiParam(value = "Length differences in app names", format = "integer")
            String accuracy) {
        int acc;
        try {
            acc = Integer.parseInt(accuracy);
        }
        catch (NumberFormatException ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var id = new AtomicLong(0);
        List<SearchApp> result;
        try {
            final var googlePlayApps = googlePlayScraper.search(query);
            List<SearchApp> list = new ArrayList<>();
            long limit = 10;
            for (SearchAppImplInfo x : googlePlayApps) {
                SearchApp searchApp = SearchApp.fromGoogleApp(x, null);
                searchApp.setId(id.addAndGet(1));
                if (limit-- == 0) break;
                var parsedUrl = UserUrlParser.parseGooglePlayUrl(searchApp.getLinkGooglePlay());
                if (appService.existByMarketId(parsedUrl.get("id"), "", "")){
                    searchApp.setTracking(true);
                }
                list.add(searchApp);
            }
            result = list;
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
                    asApp.setLinkAppStore(appStoreSrc);
                    asApp.setName(app.name);
                    asApp.setAvatarSrc(app.imageSrc);
                    if(appService.existByMarketId("", app.id, "")) {
                        asApp.setTracking(true);
                    }
                    asApp.setId(id.addAndGet(1));
                    result.add(asApp);
                }
                else {
                    appMostAccuracy.setLinkAppStore(appStoreSrc);
                }
            }
        } catch (IOException | URISyntaxException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
