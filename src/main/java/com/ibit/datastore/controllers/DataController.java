package com.ibit.datastore.controllers;

import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import com.ibit.datastore.services.CatalogueService;
import com.ibit.datastore.services.CatalogueServiceAsync;
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

        return Mono.fromFuture(() -> catalogueService.queryCatalogueItem(request))
                .map(ResponseEntity::ok)
                .onErrorResume(NoSuchElementException.class, e -> QueryResponse.notFound(request, e.getMessage(), HttpStatus.NOT_FOUND))
                .onErrorResume(Exception.class, e -> QueryResponse.badRequest(request, e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/queryCached")
    public Mono<ResponseEntity<QueryResponse>> getCatalogueItem(@RequestParam String cacheKey) {

        return Mono.fromFuture(() -> catalogueService.queryCatalogueItem(cacheKey))
                .map(ResponseEntity::ok)
                .onErrorResume(NoSuchElementException.class, e -> QueryResponse.notFound(cacheKey, e.getMessage(), HttpStatus.NOT_FOUND))
                .onErrorResume(Exception.class, e -> QueryResponse.badRequest(cacheKey, e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/queryAsync")
    public Mono<ResponseEntity<QueryResponse>> getCatalogueItemAsync(@ModelAttribute QueryRequest request) {

        return Mono.fromFuture(() -> catalogueServiceAsync.queryCatalogueItem(request))
                .map(ResponseEntity::ok)
                .onErrorResume(NoSuchElementException.class, e -> QueryResponse.notFound(request, e.getMessage(), HttpStatus.NOT_FOUND))
                .onErrorResume(Exception.class, e -> QueryResponse.badRequest(request, e.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // Connect to the websockets using url and topic
    //Url:"ws://LAPTOP-UMF83CB2:8007/ws-endpoint1"
    //Topic: "/topic/queryAsync"
    // Send a message to the websocket
    // Message: {"catalogue":"catalogue1","catalogueItem":"catalogueItem1","queryArgs":["arg1","arg2"]}
    // The response will be sent to the topic "/topic/queryAsync"
    // The response will be of type QueryResponse
    // Sample Function:
    // function sendName() {
    //     stompClient.publish({
    //             destination: "/app/sendQueryResponse",
    //             body: JSON.stringify(Message)
    // });

    @MessageMapping("/sendQueryResponse")
    @SendTo("/topic/queryAsync")
    public QueryResponse queryResponse(QueryRequest request) {

        try {
            logger.info("Sending WebSocket Notification:" + request);
            var response = catalogueServiceAsync.queryCatalogueItem(request).join();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

