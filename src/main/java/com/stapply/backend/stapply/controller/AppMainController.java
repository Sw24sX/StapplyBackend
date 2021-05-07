package com.stapply.backend.stapply.controller;

import com.stapply.backend.stapply.models.AppMain;
import com.stapply.backend.stapply.service.AppMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/apps/{id}")
    public ResponseEntity<?> getAppMain(@PathVariable(name = "id")Long id) {
        final var result = appService.findById(id);
        return result != null ?
                new ResponseEntity<>(result, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/apps/{id}")
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

    @DeleteMapping("/apps/{id}")
    public ResponseEntity<?> deleteAppMain(@PathVariable(name = "id")Long id) {
        final var result = appService.delete(id);
        return result ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
