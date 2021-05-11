package com.stapply.backend.stapply.controller.main;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.service.AppMainService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/apps")
public class AppMainController {
    private final AppMainService appService;

    @Autowired
    public AppMainController(AppMainService appService) {
        this.appService = appService;
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
        if(allApps == null || allApps.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        final var result = allApps.stream().map(AppMainPage::new);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get one app by id", response = AppMain.class)
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppMain(@PathVariable(name = "id")Long id) {
        final var result = appService.findById(id);
        return result != null ?
                new ResponseEntity<>(result, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Change app name by id")
    @PutMapping("/{id}")
    public ResponseEntity<?> changeName(@PathVariable(name = "id")Long id, @RequestBody AppMain appWithNewName) {
        final var app = appService.findById(id);
        if(app == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //todo validate name
        app.setName(appWithNewName.getName());
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

    @ApiOperation(value = "Test custom output")
    @GetMapping("/test/{id}")
    public ResponseEntity<?> getTestAppMain(@PathVariable(name = "id")Long id) {
        final var result = new AppMainPage(appService.findById(id));
        return result != null ?
                new ResponseEntity<>(result, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Test custom output")
    @GetMapping("/test")
    public ResponseEntity<Stream<AppMainPage>> getAllTestAppMain() {
        final var result = appService.findAll().stream().map(AppMainPage::new);
        return result != null ?
                new ResponseEntity<>(result, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
