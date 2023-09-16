package com.ibit.datastore.services;

import com.ibit.datastore.cache.MemoryCache;
import com.ibit.datastore.config.AppConfig;
import com.ibit.datastore.factory.ProviderFactory;
import com.ibit.datastore.models.CatalogueItem;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import io.swagger.annotations.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.ibit.datastore.helpers.Constants.CATALOGUE_NOT_FOUND;
import static com.ibit.datastore.helpers.Constants.CATALOGUE_SOURCE_CACHED;

@Service
@Scope(name = "prototype", description = "CatalogueServiceImpl")
public class CatalogueServiceImpl implements CatalogueService {

    @Autowired
    MemoryCache memoryCache;
    @Autowired
    CatalogueProvider queryService;
    @Autowired
    AppConfig appConfig;

    @Autowired
    ProviderFactory providerFactory;

    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        var cItem = appConfig.getCatalogueItem(request.getCatalogue(), request.getCatalogueItem());
        if (cItem == null)
            throw new NoSuchElementException(CATALOGUE_NOT_FOUND);

        if (request.getQueryArgs() != null && request.getQueryArgs().length > 0) {
            var query = String.format(cItem.getQuery(), request.getQueryArgs());
            cItem.setQuery(query);
        }
        request.setCatalogueItemInstance(cItem);

        var provider = providerFactory.getCatalogueProvider(request);
        return request.isSkipCache() ?
                getResponse(cItem, () -> provider.get().queryCatalogueItem(request)) :
                getCachedResponse(cItem, () -> provider.get().queryCatalogueItem(request));

    }

    @Override
    public void clearCatalogue(String catalogue, String catalogueItem) {
        var cItem = appConfig.getCatalogueItem(catalogue, catalogueItem);
        if (cItem == null)
            throw new NoSuchElementException(CATALOGUE_NOT_FOUND);

        memoryCache.remove(cItem.getCacheKey());
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
        return CompletableFuture.completedFuture(response.clone());
    }

    private CompletableFuture<QueryResponse> getResponse(CatalogueItem catalogueItem, Supplier<CompletableFuture<QueryResponse>> func) {
        return func.get();
    }

    public String getCacheKey(CatalogueItem catalogueItem) {
        return catalogueItem.getCacheKey();
    }

}

