package com.ibit.datastore.services;

import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;

import java.util.concurrent.CompletableFuture;

public interface CatalogueService {
    CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request);

    void clearCatalogue(String catalogue, String catalogueItem);

}
