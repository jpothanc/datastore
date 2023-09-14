package com.jpothanc.controllers;

import com.jpothanc.models.QueryRequest;
import com.jpothanc.models.QueryResponse;
import com.jpothanc.services.CatalogueService;
import com.jpothanc.services.CatalogueProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.jpothanc.helpers.CatalogueHelper.*;


@RestController
@RequestMapping("api/v1/data")
public class DataController {
    @Autowired
    CatalogueService catalogueService;
    @GetMapping("/")
    public Mono<ResponseEntity<String>> get() throws ExecutionException, InterruptedException {

          return Mono.just(ResponseEntity.ok("DataStore"));
    }
    @GetMapping("/query")
    public Mono<ResponseEntity<QueryResponse>> getCatalogueItem(@ModelAttribute QueryRequest request) {

        try {
            var response = catalogueService.queryCatalogueItem(request).join();
            return Mono.just(ResponseEntity.ok(response));

        } catch (NoSuchElementException e) {
            return Mono.just(QueryResponse.notFound(request, e.getMessage(), HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            return Mono.just(QueryResponse.badRequest(request, e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
}

