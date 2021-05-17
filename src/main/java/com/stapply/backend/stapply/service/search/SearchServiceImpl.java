package com.stapply.backend.stapply.service.search;

import com.stapply.backend.stapply.controller.search.webmodel.SearchApp;
import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.parser.UserUrlParser;
import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.parser.scraper.search.SearchAppImplInfo;
import com.stapply.backend.stapply.repository.AppRepository;
import com.stapply.backend.stapply.service.search.servicemodel.AppMarketId;
import com.stapply.backend.stapply.service.search.servicemodel.AppMarketSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SearchServiceImpl implements SearchService{
    private final AppRepository appRepository;
    private final StoreScraper googlePlayScraper;
    private final StoreScraper appStoreScraper;

    @Autowired
    public SearchServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
        this.appStoreScraper = ScraperFabric.AppStoreScraper();
        this.googlePlayScraper = ScraperFabric.GooglePlayScraper();
    }

    @Override
    public List<AppMarketSearch> search(String query, Integer accuracy) {
        var id = new AtomicLong(0);
        List<AppMarketSearch> result;
        try {
            final var googlePlayApps = googlePlayScraper.search(query);
            List<AppMarketSearch> list = new ArrayList<>();
            long limit = 10;
            for (var gp : googlePlayApps) {
                var searchApp = new AppMarketSearch();
                searchApp.putDataFromParsedSearchApp(gp, id.addAndGet(1));
                searchApp.setLinkGooglePlay(gp);

                if (limit-- == 0)
                    break;

                var parsedUrl = UserUrlParser.parseGooglePlayUrl(searchApp.getLinkGooglePlay());
                if (appRepository.existsByGooglePlayId(parsedUrl.get("id"))) {
                    searchApp.setTracking(true);
                }
                list.add(searchApp);
            }
            result = list;
        } catch (Exception exception) {
            return new ArrayList<>();
        }

        try {
            final var appStoreApps = appStoreScraper.search(query);
            for (var app : appStoreApps) {
                var mostAccuracy = Integer.MAX_VALUE;
                AppMarketSearch appMostAccuracy = null;
                for(var gp : result){
                    if(gp.getLinkAppStore() != null)
                        continue;

                    var distance = gp.calculate(app.name);
                    if(distance <= accuracy && distance < mostAccuracy) {
                        mostAccuracy = distance;
                        appMostAccuracy = gp;
                    }
                }
                var appStoreSrc = "https://apps.apple.com/ru/app/" + app.id;
                if(appMostAccuracy == null) {
                    var asApp = new AppMarketSearch();
                    asApp.putDataFromParsedSearchApp(app, id.addAndGet(1));
                    if(appRepository.existsByAppStoreId(app.id)) {
                        asApp.setTracking(true);
                    }
                    result.add(asApp);
                }
                else {
                    appMostAccuracy.setLinkAppStore(appStoreSrc);
                }
            }
        } catch (IOException | URISyntaxException exception) {
            return new ArrayList<>();
        }
        return result;
    }
}
