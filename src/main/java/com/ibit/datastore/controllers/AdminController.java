package com.ibit.datastore.controllers;

import com.ibit.datastore.config.AppConfig;
import com.ibit.datastore.models.Catalogue;
import com.ibit.datastore.services.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    CatalogueService catalogueService;

    @GetMapping("/catalogues")
    public ResponseEntity<Map<String, Catalogue>> getConfig() {
        return ResponseEntity.ok(appConfig.getCatalogues());
    }

    @GetMapping("/clear/{catalogue}/{catalogueItem}")
    public ResponseEntity<String> clearCache(@PathVariable String catalogue,
                                             @PathVariable String catalogueItem) {

        catalogueService.clearCatalogue(catalogue, catalogueItem);
        return ResponseEntity.ok("Success");
    }
}
