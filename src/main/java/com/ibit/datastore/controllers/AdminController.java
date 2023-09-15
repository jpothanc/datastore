package com.ibit.datastore.controllers;

import com.ibit.datastore.config.AppSettings;
import com.ibit.datastore.models.Catalogue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    AppSettings appSettings;

    @GetMapping("/catalogues")
    public ResponseEntity<Map<String, Catalogue>> getConfig(){
        return ResponseEntity.ok(appSettings.getCatalogues());
    }
}
