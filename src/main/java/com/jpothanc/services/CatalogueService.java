package com.jpothanc.services;

import com.jpothanc.models.QueryRequest;
import com.jpothanc.models.QueryResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

public interface CatalogueService {
    CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request);
}
