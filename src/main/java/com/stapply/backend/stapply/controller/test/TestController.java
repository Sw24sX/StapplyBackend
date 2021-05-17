package com.stapply.backend.stapply.controller.test;

import com.stapply.backend.stapply.controller.main.output.AppMainPage;
import com.stapply.backend.stapply.service.AppMainService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TestController {
    private final AppMainService appService;

    public TestController(AppMainService appService) {
        this.appService = appService;
    }

    @ApiOperation(value = "Test custom output")
    @GetMapping("/test/{id}")
    public ResponseEntity<?> getTestAppMain(@PathVariable(name = "id")Long id) {
        final var result = new AppMainPage(appService.findById(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Test custom output")
    @GetMapping("/test")
    public ResponseEntity<?> getAllTestAppMain() {
        final var result = appService.findAll();
        var t = new ArrayList<String>();
        t.add("https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw");
        for (var app : result) {
            app.setImageSrcList(t);
            appService.update(app.getId(), app);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
