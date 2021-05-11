package com.stapply.backend.stapply.controller.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PutMapping("/test")
    public ResponseEntity<?> testPut(@RequestBody TestTask task) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
