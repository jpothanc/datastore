package com.ibit.datastore.services;

import com.ibit.datastore.models.Enums.CatalogueProviders;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;

import java.util.concurrent.CompletableFuture;

public interface CatalogueProvider {
    CatalogueProviders getName();
    CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request);
}
