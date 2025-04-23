package com.github.developerchml.evdbackend.infrastruct.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/status")
public class APIController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String status(){
        return "ACTIVE";
    }
}
