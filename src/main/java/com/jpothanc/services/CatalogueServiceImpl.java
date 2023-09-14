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

import static com.jpothanc.helpers.Constants.*;

@Service
@Scope(name = "prototype", description = "")
public class CatalogueServiceImpl implements CatalogueService {

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
        if (catalogueItem == null)
            throw new NoSuchElementException(CATALOGUE_NOT_FOUND);

        var provider = providerFactory.getCatalogueProvider(request);
        return request.isSkipCache() ?
                getResponse(catalogueItem, () -> provider.get().queryCatalogueItem(request)) :
                getCachedResponse(catalogueItem, () -> provider.get().queryCatalogueItem(request));

    }

    private CompletableFuture<QueryResponse> getCachedResponse(CatalogueItem catalogueItem, Supplier<CompletableFuture<QueryResponse>> func) {
        var response = memoryCache.get(catalogueItem.getCacheKey());
        if (response == null) {
            response = func.get().join();
            var cacheKey = catalogueItem.getCacheKey();
            response.setCacheKey(cacheKey);
            memoryCache.add(cacheKey, response);
        } else {
            response.setSource(CATALOGUE_SOURCE_CACHED);
        }
        return CompletableFuture.completedFuture(response);
    }

    private CompletableFuture<QueryResponse> getResponse(CatalogueItem catalogueItem, Supplier<CompletableFuture<QueryResponse>> func) {
        return func.get();
    }

    public  String getCacheKey(CatalogueItem catalogueItem){
        return catalogueItem.getCacheKey();
    }

}

