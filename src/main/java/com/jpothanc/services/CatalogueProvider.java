package com.jpothanc.services;

import com.jpothanc.models.CatalogueItem;
import com.jpothanc.models.Enums;
import com.jpothanc.models.Enums.CatalogueProviders;
import com.jpothanc.models.QueryRequest;
import com.jpothanc.models.QueryResponse;

import java.util.concurrent.CompletableFuture;

public interface CatalogueProvider {
    CatalogueProviders getName();
    CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request);
}
