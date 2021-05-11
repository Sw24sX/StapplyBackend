package com.stapply.backend.stapply.controller.test;

import com.stapply.backend.stapply.service.AppMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PutMapping("/test")
    public ResponseEntity<?> testPut() {


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
