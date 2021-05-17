package com.stapply.backend.stapply.service.appmain;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.parser.UserUrlParser;
import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.parser.scraper.detailed.FullAppImplInfo;
import com.stapply.backend.stapply.repository.AppRepository;
import com.stapply.backend.stapply.service.appmain.servicemodels.AppLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if (app.getGooglePlayAppLink() != null && !app.getGooglePlayAppLink().isEmpty()) {
            HashMap<String, String> parsedGooglePlayUrls;
            try {
                parsedGooglePlayUrls = UserUrlParser.parseGooglePlayUrl(app.getGooglePlayAppLink());
            } catch (Exception e) {
                return null;
            }

            if (!UserUrlParser.googlePlayUrlIsValid(parsedGooglePlayUrls))
                return null;

            var googlePlayDetailedApp = new FullAppImplInfo();
            try {
                googlePlayDetailedApp = googlePlayScraper.detailed(parsedGooglePlayUrls.get("id"));
            } catch (ParseException | IOException | URISyntaxException e) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            appMain.setImageSrcList(googlePlayDetailedApp.images);
            appMain.setDeveloper(googlePlayDetailedApp.developer);
            appMain.setName(app.getName());
            appMain.setAvatarSrc(googlePlayDetailedApp.imageSrc);
            appMain.setGooglePlayId(parsedGooglePlayUrls.get("id"));
            appMain.setScoreGooglePlay(googlePlayDetailedApp.score);
            appMain.setScoreGooglePlay(googlePlayDetailedApp.score);
            //var lengthDescription = Math.min(googlePlayDeta   iledApp.description.length(), 1500);
            //appMain.setDescription(googlePlayDetailedApp.description.substring(0, lengthDescription));
        }

        if(app.getAppStoreAppLik() != null) {
            var id = "";
            try {
                id = UserUrlParser.getIdFromAppStoreUrl(app.getAppStoreAppLik());
            } catch (Exception e) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            var appStoreFullApp = new FullAppImplInfo();
            try {
                appStoreFullApp = appStoreScraper.detailed(id);
            } catch (ParseException | IOException | URISyntaxException e) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            appMain.setScoreAppStore(appStoreFullApp.score);
            appMain.setAppStoreId(id);
            if(appMain.getName() == null)
                appMain.setName(appStoreFullApp.name);
            if(appMain.getAvatarSrc() == null)
                appMain.setAvatarSrc(appStoreFullApp.imageSrc);
            if(appMain.getDeveloper() == null)
                appMain.setDeveloper(appStoreFullApp.developer);
            //if(appMain.getDescription() == null)
            //    appMain.setDescription(appStoreFullApp.description);
            if(appMain.getImageSrcList().isEmpty())
                appMain.setImageSrcList(appStoreFullApp.images);
        }
        return create(appMain);
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
