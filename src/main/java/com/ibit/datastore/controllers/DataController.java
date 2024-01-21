package com.ibit.datastore.controllers;

import com.ibit.datastore.helpers.Constants;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import com.ibit.datastore.services.CatalogueServiceAsync;
import com.ibit.datastore.services.CatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("api/v1/data")
@CrossOrigin(origins = "*")
public class DataController {

    CatalogueService catalogueService;
    CatalogueServiceAsync catalogueServiceAsync;

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    public DataController(CatalogueService catalogueService, CatalogueServiceAsync catalogueServiceAsync) {
        this.catalogueService = catalogueService;
        this.catalogueServiceAsync = catalogueServiceAsync;
    }

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

    @GetMapping("/queryCached")
    public Mono<ResponseEntity<QueryResponse>> getCatalogueItem(@RequestParam String cacheKey) {

        try {
            var response = catalogueService.queryCatalogueItem(cacheKey).join();
            return Mono.just(ResponseEntity.ok(response));

        } catch (NoSuchElementException e) {
            return Mono.just(QueryResponse.notFound(cacheKey, e.getMessage(), HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            return Mono.just(QueryResponse.badRequest(cacheKey, e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/queryAsync")
    public Mono<ResponseEntity<QueryResponse>> getCatalogueItemAsync(@ModelAttribute QueryRequest request) {

        try {
            var response = catalogueServiceAsync.queryCatalogueItem(request).join();
            return Mono.just(ResponseEntity.ok(response));

        } catch (NoSuchElementException e) {
            return Mono.just(QueryResponse.notFound(request, e.getMessage(), HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            return Mono.just(QueryResponse.badRequest(request, e.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }
    //"healthcheckWsEndpoint": "wss://healthcheck-ib.azurewebsites.net/ws-endpoint1",
    //"healthcheckWsTopic": "/topic/healthCheck",
    @MessageMapping(Constants.ASYNC_QUERY_SOCKET_INCOMING_MESSAGE)
    @SendTo("/topic/queryAsync")
    public QueryResponse queryAsyncResponse(QueryResponse queryResponse) {

        try {
            logger.info("Sending WebSocket Notification:" + queryResponse);
            return queryResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

