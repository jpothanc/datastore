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

    @GetMapping("/async")
    public CompletableFuture<ResponseEntity<String>> performAsyncOperation() {
        // Simulate an asynchronous operation
        CompletableFuture<ResponseEntity<String>> future = CompletableFuture.supplyAsync(() -> {
            // Your asynchronous logic here
            String result = "Async operation completed";
            return ResponseEntity.ok(result);
        });

        return future;
    }
    @GetMapping("/")
    @Async
    public Mono<ResponseEntity<String>> get() throws ExecutionException, InterruptedException {

          return Mono.just(ResponseEntity.ok("DataStore"));
          // Your asynchronous logic here
            //return Mono.just(ResponseEntity.ok(catalogueService.queryCatalogueItem("","").join()));


//        JsonObject jsonObject = new JsonObject();
//        // Add key-value pairs to the JSON object
//        jsonObject.addProperty("name", "John");
//        jsonObject.addProperty("age", 30);
//        jsonObject.addProperty("city", "New York");
//        List<JsonObject> res =  new ArrayList<JsonObject>();
//        res.add(jsonObject);
//        var response = new QueryResponse();
//        response.setResult(res);
//        //return new ResponseEntity<>(res, HttpStatus.OK);
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @GetMapping("/{catalogue}/{catalogueItem}")
//    public ResponseEntity<QueryResponse> getCatalogueItem(@PathVariable("catalogue") String catalogue, @PathVariable("catalogueItem") String catalogueItem) {
//        QueryResponse response = null;
//        response = catalogueService.get(catalogue, catalogueItem).join();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    @GetMapping("/query")
    public Mono<ResponseEntity<QueryResponse>> getCatalogueItem(@ModelAttribute QueryRequest request) {
        var catalogueKey = getCatalogueKey(request);

        try {
//            if(request.isCancel())
//                return Mono.just(ResponseEntity.ok(""));
            var response = catalogueService.queryCatalogueItem(request).join();
            return Mono.just(ResponseEntity.ok(response));
        }catch (NoSuchElementException  e)
        {
            return Mono.just(QueryResponse.notFound(catalogueKey, e.getMessage(), HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            return Mono.just(QueryResponse.badRequest(catalogueKey, e.getMessage(), HttpStatus.BAD_REQUEST));
        } finally {
        }
    }
}

