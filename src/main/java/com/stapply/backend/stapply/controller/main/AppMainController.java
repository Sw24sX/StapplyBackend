package com.stapply.backend.stapply.controller.main;

import com.stapply.backend.stapply.controller.main.webmodel.AppMainPage;
import com.stapply.backend.stapply.controller.main.webmodel.AppName;
import com.stapply.backend.stapply.controller.search.webmodel.AddApp;
import com.stapply.backend.stapply.controller.search.webmodel.AppId;
import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.parser.mainscraper.ScraperFabric;
import com.stapply.backend.stapply.parser.scraper.StoreScraper;
import com.stapply.backend.stapply.service.appmain.AppMainService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        final var allApps = appService.findAll();
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

    @PostMapping("/add")
    @ApiOperation(value = "Add app")
    public ResponseEntity<?> addApp(@RequestBody AddApp requestBody) {
        if(!AddApp.isValid(requestBody))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        final var result = appService.create(AddApp.toAppLinks(requestBody));
        return result != null ?
                new ResponseEntity<>(new AppId(result.getId()), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
