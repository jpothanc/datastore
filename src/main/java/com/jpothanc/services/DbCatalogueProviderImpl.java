package com.jpothanc.services;

import com.google.gson.JsonObject;
import com.jpothanc.models.CatalogueItem;
import com.jpothanc.models.Enums;
import com.jpothanc.models.Enums.CatalogueProviders;
import com.jpothanc.models.QueryRequest;
import com.jpothanc.models.QueryResponse;
import io.swagger.annotations.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Scope(name = "prototype", description = "")
public class DbCatalogueProviderImpl implements CatalogueProvider {
    @Override
    public CatalogueProviders getName() {
        return CatalogueProviders.Database;
    }

    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        System.out.println("Querying Catalogue " + request.getCatalogueItem() + " using :" + getName().toString());

        List<JsonObject> res =  new ArrayList<JsonObject>();

        JsonObject jsonObject = new JsonObject();
        // Add key-value pairs to the JSON object
        jsonObject.addProperty("name", "John");
        jsonObject.addProperty("age", 30);
        jsonObject.addProperty("city", "New York");
        res.add(jsonObject);
        var response =  QueryResponse.createOkRespose(request);
        response.setResult(res);
        response.setRecords(res.size());
        return CompletableFuture.completedFuture(response);
    }
}
