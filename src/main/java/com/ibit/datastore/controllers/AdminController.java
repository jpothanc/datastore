package com.ibit.datastore.controllers;

import com.ibit.datastore.config.AppConfig;
import com.ibit.datastore.models.Catalogue;
import com.ibit.datastore.services.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    CatalogueService catalogueService;

    @GetMapping("/catalogues")
    public ResponseEntity<Map<String, Catalogue>> getConfig() {
        return ResponseEntity.ok(appConfig.getCatalogues());
    }

    @GetMapping("/clearCache")
    public ResponseEntity<String> clearCache(@RequestParam String catalogue,
                                             @RequestParam String catalogueItem) {

        catalogueService.clearCatalogue(catalogue, catalogueItem);
        return ResponseEntity.ok("Success");
    }
}
