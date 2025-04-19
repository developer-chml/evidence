package com.github.developerchml.evdbackend.infrastruct.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/availability")
public class APIController {

    @GetMapping
    public ResponseEntity<Map<String, Boolean>> availability(){
        Map<String, Boolean> services = Map.of("api",true);
        return ResponseEntity.ok().body(services);
    }
}
