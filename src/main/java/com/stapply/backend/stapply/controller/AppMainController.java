package com.stapply.backend.stapply.controller;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.service.AppMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppMainController {
    @Autowired
    private AppMainService appService;

    @GetMapping("/apps")
    public ResponseEntity<List<AppMain>> getAllAppsMain() {
        final var result = appService.findAll();
        return result != null && !result.isEmpty() ?
                new ResponseEntity<>(result, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
