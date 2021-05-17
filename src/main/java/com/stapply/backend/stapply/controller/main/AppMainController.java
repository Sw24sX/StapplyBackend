package com.stapply.backend.stapply.controller.main;

import com.stapply.backend.stapply.controller.main.output.AppMainPage;
import com.stapply.backend.stapply.controller.main.output.AppName;
import com.stapply.backend.stapply.controller.search.output.AddApp;
import com.stapply.backend.stapply.controller.search.output.AppId;
import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.parser.UserUrlParser;
import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.parser.scraper.detailed.FullAppImplInfo;
import com.stapply.backend.stapply.service.AppMainService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/apps")
public class AppMainController {
    private final AppMainService appService;
    private final StoreScraper googlePlayScraper;
    private final StoreScraper appStoreScraper;

    //@Autowired
    public AppMainController(AppMainService appService) {
        this.appService = appService;
        this.appStoreScraper = ScraperFabric.AppStoreScraper();
        this.googlePlayScraper = ScraperFabric.GooglePlayScraper();
    }

    @ApiOperation(value = "Get all apps for main page", response = AppMain[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<Stream<AppMainPage>> getAllAppsMain() {
        var allApps = appService.findAll();
        if(allApps == null)
            allApps = new ArrayList<>();

        final var result = allApps.stream().map(AppMainPage::new);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get one app by id", response = AppMain.class)
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppMain(@PathVariable(name = "id")Long id) {
        final var app = appService.findById(id);
        if(app == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var result = new AppMainPage(app);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Change app name by id")
    @PutMapping("/{id}")
    public ResponseEntity<?> changeName(@PathVariable(name = "id")Long id, @RequestBody AppName name) {
        final var app = appService.findById(id);
        if(app == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //todo validate name
        app.setName(name.getName());
        final var result = appService.update(id, app);
        return result ?
                new ResponseEntity<>(HttpStatus.ACCEPTED) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Delete app by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppMain(@PathVariable(name = "id")Long id) {
        final var result = appService.delete(id);
        return result ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Refresh data base")
    @PostMapping("/dev/refresh")
    public ResponseEntity<?> refreshDataBase() {
        final var result = appService.findAll();
        for (var app : result) {
            appService.delete(app.getId());
        }

        var name = "Twitch_%s";
        var developer = "witch %s Interactive, Inc.";
        var avatarSrc = "https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw";
        var imgSources = new ArrayList<String>();
        imgSources.add("https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw");
        for (var i = 0; i < 11; i++) {
            var app = new AppMain();
            app.setName(String.format(name, i));
            app.setDeveloper(String.format(developer, i));
            app.setAvatarSrc(avatarSrc);
            app.setImageSrcList(imgSources);
            appService.create(app);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add app")
    public ResponseEntity<?> addApp(@RequestBody AddApp requestBody) {
        if(!AddApp.isValid(requestBody))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var appMain = new AppMain();
        if(requestBody.getGooglePlayAppLink() != null && !requestBody.getGooglePlayAppLink().isEmpty()) {
            HashMap<String, String> parsedGooglePlayUrls;
            try {
                parsedGooglePlayUrls = UserUrlParser.parseGooglePlayUrl(requestBody.getGooglePlayAppLink());
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            if(!UserUrlParser.googlePlayUrlIsValid(parsedGooglePlayUrls))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            var googlePlayDetailedApp = new FullAppImplInfo();
            try {
                googlePlayDetailedApp = googlePlayScraper.detailed(parsedGooglePlayUrls.get("id"));
            } catch (ParseException | IOException | URISyntaxException e) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            appMain.setImageSrcList(googlePlayDetailedApp.images);
            appMain.setDeveloper(googlePlayDetailedApp.developer);
            appMain.setName(requestBody.getName());
            appMain.setAvatarSrc(googlePlayDetailedApp.imageSrc);
            appMain.setGooglePlayId(parsedGooglePlayUrls.get("id"));
            appMain.setScoreGooglePlay(googlePlayDetailedApp.score);
            appMain.setScoreGooglePlay(googlePlayDetailedApp.score);
            //var lengthDescription = Math.min(googlePlayDeta   iledApp.description.length(), 1500);
            //appMain.setDescription(googlePlayDetailedApp.description.substring(0, lengthDescription));
        }

        if(requestBody.getAppStoreAppLik() != null) {
            var id = "";
            try {
                id = UserUrlParser.getIdFromAppStoreUrl(requestBody.getAppStoreAppLik());
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

        appService.create(appMain);

        return new ResponseEntity<>(new AppId(appMain.getId()), HttpStatus.OK);
    }
}
