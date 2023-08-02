package com.pocekt.art.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ar")
public class ARController {
    @GetMapping("")
    public ResponseEntity<?> testAR(){
        return ResponseEntity.ok().body("ArTest");
    }
}
