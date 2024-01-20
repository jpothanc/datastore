package com.ibit.datastore.services;

import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.CompletableFuture;

public interface CatalogueServiceAsync {
    CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request);
    Observable<QueryResponse> subscribe();
}
