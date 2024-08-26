package com.ibit.datastore.controllers;

import com.ibit.datastore.config.AppConfig;
import com.ibit.datastore.models.Catalogue;
import com.ibit.datastore.services.CatalogueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin(origins = "*")
@Tag(name = "Admin Controller", description = "Admin Controller")
public class AdminController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    CatalogueService catalogueService;

    @GetMapping("/catalogues")
    @Operation(summary = "Get all catalogues")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<Map<String, Catalogue>> getConfig() {
        return ResponseEntity.ok(appConfig.getCatalogues());
    }

    @GetMapping("/clearCache")
    @Operation(summary = "Clear cache for a specific catalogue")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<String> clearCache(@RequestParam String catalogue,
                                             @RequestParam String catalogueItem) {

        catalogueService.clearCatalogue(catalogue, catalogueItem);
        return ResponseEntity.ok("Success");
    }
}
