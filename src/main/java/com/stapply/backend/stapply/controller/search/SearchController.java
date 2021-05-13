package com.stapply.backend.stapply.controller.search;

import com.stapply.backend.stapply.models.AppMain;
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

    @PostMapping("")
    public ResponseEntity<?> addApp(@RequestBody AppMain appt) {
        var app = new AddApp();
        if(!AddApp.isValid(app))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var appMain = new AppMain();
        if(app.getGooglePlayAppLink() != null) {
            HashMap<String, String> parsedGooglePlayUrls;
            try {
                parsedGooglePlayUrls = parseGooglePlayUrl(app.getGooglePlayAppLink());
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if(!googlePlayUrlIsValid(parsedGooglePlayUrls))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            var googlePlayDetailedApp = new FullAppImplInfo();
            try {
                googlePlayDetailedApp = googlePlayScraper.detailed(parsedGooglePlayUrls.get("id"));
            } catch (ParseException | IOException | URISyntaxException e) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            appMain.setImageSrcList(googlePlayDetailedApp.images);
            appMain.setDeveloper(googlePlayDetailedApp.developer);
            appMain.setName(googlePlayDetailedApp.name);
            appMain.setAvatarSrc(googlePlayDetailedApp.imageSrc);
            appMain.setGooglePlaySrc(app.getGooglePlayAppLink());
            appMain.setScoreGooglePlay(googlePlayDetailedApp.score);
        }

        appService.create(appMain);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean googlePlayUrlIsValid(HashMap<String, String> url) {
        var example = "https://play.google.com/store/apps/details";
        return url.containsKey("id") && url.containsKey("base") && !url.get("base").equals(example);
    }

    private HashMap<String, String> parseGooglePlayUrl(String url) throws Exception {
        var result = new HashMap<String, String>();
        var baseAndParams = url.split("\\?");
        if(baseAndParams.length != 2)
            throw new Exception();
        result.put("base", baseAndParams[0]);
        var params = baseAndParams[1].split("&");
        for(var param : params) {
            var nameAndValue = param.split("=");
            if(nameAndValue.length != 2)
                throw new Exception();
            result.put(nameAndValue[0], nameAndValue[1]);
        }
        return result;
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
