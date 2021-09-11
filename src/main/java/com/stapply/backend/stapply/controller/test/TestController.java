package com.stapply.backend.stapply.controller.test;

import com.stapply.backend.stapply.controller.main.webmodel.AppMainPage;
import com.stapply.backend.stapply.domain.AppMain;
import com.stapply.backend.stapply.service.appmain.AppMainService;
import io.swagger.annotations.ApiOperation;
import lombok.var;
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
}
