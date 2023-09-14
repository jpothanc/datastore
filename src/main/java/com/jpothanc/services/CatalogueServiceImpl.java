package com.jpothanc.services;

import com.jpothanc.cache.MemoryCache;
import com.jpothanc.config.AppSettings;
import com.jpothanc.factory.ProviderFactory;
import com.jpothanc.models.CatalogueItem;
import com.jpothanc.models.QueryRequest;
import com.jpothanc.models.QueryResponse;
import io.swagger.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
@Scope(name = "prototype", description = "")
public class CatalogueServiceImpl implements CatalogueService{

    @Autowired
    MemoryCache memoryCache;
    @Autowired
    CatalogueProvider queryService;
    @Autowired
    AppSettings appSettings;

    @Autowired
    ProviderFactory providerFactory;
    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        var catalogueItem = appSettings.getCatalogueItem(request.getCatalogue(), request.getCatalogueItem());
        if(catalogueItem == null)
            throw new NoSuchElementException("Catalogue Item not found");

        var provider = providerFactory.getCatalogueProvider(request);
        return request.isSkipCache() ?
                getResponse(catalogueItem, () -> provider.get().queryCatalogueItem(request)) :
                getCachedResponse(catalogueItem, () -> provider.get().queryCatalogueItem(request)) ;

    }

    private CompletableFuture<QueryResponse> getCachedResponse(CatalogueItem catalogueItem, Supplier<CompletableFuture<QueryResponse>> func) {
        System.out.println("getCachedResponse " + catalogueItem.getKey());
        var res =  memoryCache.get(catalogueItem.getKey());
        if(res == null)
        {
            System.out.println("returning from queryService");
            res = func.get().join();
            memoryCache.add(catalogueItem.getKey(),res);
        }
        System.out.println("returning from cache");
        return CompletableFuture.completedFuture(res);
    }
    private CompletableFuture<QueryResponse> getResponse(CatalogueItem catalogueItem, Supplier<CompletableFuture<QueryResponse>> func) {
            System.out.println("getResponse " + catalogueItem.getKey());
            return func.get();
    }
}

