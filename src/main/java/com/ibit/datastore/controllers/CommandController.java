package com.ibit.datastore.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/command")
public class CommandController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
