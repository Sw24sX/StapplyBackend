package com.stapply.backend.stapply.service.appmain;

import com.stapply.backend.stapply.domain.AppMain;
import com.stapply.backend.stapply.parser.UserUrlParser;
import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.parser.scraper.detailed.FullAppImplInfo;
import com.stapply.backend.stapply.repository.AppRepository;
import com.stapply.backend.stapply.service.appmain.servicemodels.AppLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AppMainServiceImpl implements AppMainService{
    private final AppRepository appRepository;
    private final StoreScraper googlePlayScraper;
    private final StoreScraper appStoreScraper;

    @Autowired
    public AppMainServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
        this.appStoreScraper = ScraperFabric.AppStoreScraper();
        this.googlePlayScraper = ScraperFabric.GooglePlayScraper();
    }

    @Override
    public List<AppMain> findAll() {
        var result =  appRepository.findAll();
        return result == null ? new ArrayList<>() : result;
    }

    @Override
    public AppMain findById(Long id) {
        if(!appRepository.existsById(id))
            return null;
        return appRepository.findById(id).get();
    }

    @Override
    public AppMain findByMarketId(String googlePlayId, String appStoreId, String appGalleryId) {
        googlePlayId = googlePlayId == null ? "" : googlePlayId;
        appStoreId = appStoreId == null ? "" : appStoreId;
        appGalleryId = appGalleryId == null ? "" : appGalleryId;
        if(!appRepository.existsByGooglePlayIdOrAppStoreIdOrAppGalleryId(googlePlayId, appStoreId, appGalleryId))
            return null;
        return appRepository.findByGooglePlayIdOrAppStoreIdOrAppGalleryId(googlePlayId, appStoreId, appGalleryId);
    }

    @Override
    public boolean existByMarketId(String googlePlayId, String appStoreId, String appGalleryId) {
        googlePlayId = googlePlayId == null ? "" : googlePlayId;
        appStoreId = appStoreId == null ? "" : appStoreId;
        appGalleryId = appGalleryId == null ? "" : appGalleryId;
        return appRepository.existsByGooglePlayIdOrAppStoreIdOrAppGalleryId(googlePlayId, appStoreId, appGalleryId);
    }

    @Override
    public boolean update(Long id, AppMain app) {
        if(!appRepository.existsById(id))
            return false;
        app.setId(id);
        appRepository.save(app);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        if(!appRepository.existsById(id))
            return false;
        appRepository.deleteById(id);
        return true;
    }

    @Override
    public AppMain create(AppMain app) {
        return appRepository.save(app);
    }

    @Override
    public AppMain create(AppLinks app) {
        if (!AppLinks.isValid(app))
            return null;

        var appMain = new AppMain();
        appMain.setName(app.getName());
        var googlePlayData = getGooglePlayData(app.getGooglePlayAppLink());
        if(googlePlayData != null)
            fillGooglePlayData(googlePlayData, appMain);

        var appStoreData = getAppStoreData(app.getAppStoreAppLik());
        if(appStoreData != null)
            fillAppStoreData(appStoreData, appMain);
        return create(appMain);
    }

    private FullAppImplInfo getGooglePlayData(String url) {
        if (url == null || url.isEmpty())
            return null;

        HashMap<String, String> parsedGooglePlayUrls;
        try {
            parsedGooglePlayUrls = UserUrlParser.parseGooglePlayUrl(url);
        } catch (Exception e) {
            return null;
        }

        if (!UserUrlParser.googlePlayUrlIsValid(parsedGooglePlayUrls))
            return null;

        var googlePlayDetailedApp = new FullAppImplInfo();
        try {
            googlePlayDetailedApp = googlePlayScraper.detailed(parsedGooglePlayUrls.get("id"));
        } catch (ParseException | IOException | URISyntaxException e) {
            return null;
        }
        return googlePlayDetailedApp;
    }

    private void fillGooglePlayData(FullAppImplInfo googlePlay, AppMain app) {
        putData(googlePlay, app);
        app.setScoreGooglePlay(googlePlay.score);
        app.setGooglePlayId(googlePlay.id);
    }

    private FullAppImplInfo getAppStoreData(String url) {
        if(url == null || url.isEmpty())
            return null;

        var appStoreFullApp = new FullAppImplInfo();
        try {
            var id = UserUrlParser.getIdFromAppStoreUrl(url);
            appStoreFullApp = appStoreScraper.detailed(id);
        } catch (Exception e) {
            return null;
        }
        return appStoreFullApp;
    }

    private void fillAppStoreData(FullAppImplInfo appStore, AppMain app) {
        putData(appStore, app);
        app.setScoreAppStore(appStore.score);
        app.setAppStoreId(appStore.id);
    }

    private void putData(FullAppImplInfo app, AppMain mainApp) {
        if(checkProperty(mainApp.getName()))
            mainApp.setName(app.name);

        if(checkProperty(mainApp.getAvatarSrc()))
            mainApp.setAppStoreId(app.imageSrc);

        if(checkProperty(mainApp.getDeveloper()))
            mainApp.setDeveloper(app.developer);

        if(checkProperty(mainApp.getDescription()))
        {
            var descriptionLength = app.description.length() >= 1500 ? 1499 : app.description.length();
            var description = app.description.substring(0, descriptionLength);
            mainApp.setDescription(description);
        }

//        if(mainApp.getImageSrcList() == null || mainApp.getImageSrcList().isEmpty())
//            mainApp.setImageSrcList(app.images);
    }

    private boolean checkProperty(String value) {
        return value == null || value.isEmpty();
    }

    @Override
    public AppMain findByGooglePlayId(String googlePlayId) {
        return appRepository.findByGooglePlayId(googlePlayId);
    }

    @Override
    public boolean existByGooglePlayId(String googlePlayId) {
        return appRepository.existsByGooglePlayId(googlePlayId);
    }
}
