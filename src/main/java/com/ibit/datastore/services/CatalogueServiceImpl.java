package com.ibit.datastore.services;

import com.ibit.datastore.cache.MemoryCache;
import com.ibit.datastore.config.AppConfig;
import com.ibit.datastore.factory.ProviderFactory;
import com.ibit.datastore.models.CatalogueItem;
import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.models.QueryResponse;
import com.ibit.datastore.services.providers.CatalogueProvider;
import io.swagger.annotations.Scope;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.ibit.datastore.helpers.Constants.CATALOGUE_NOT_FOUND;
import static com.ibit.datastore.helpers.Constants.CATALOGUE_SOURCE_CACHED;

@Service
@Scope(name = "prototype", description = "CatalogueServiceImpl")
public class CatalogueServiceImpl implements CatalogueService {


    private final MemoryCache memoryCache;
    private final CatalogueProvider queryService;
    private final AppConfig appConfig;
    private final ProviderFactory providerFactory;

    public CatalogueServiceImpl(MemoryCache memoryCache, CatalogueProvider queryService,
                                AppConfig appConfig, ProviderFactory providerFactory) {
        this.memoryCache = memoryCache;
        this.queryService = queryService;
        this.appConfig = appConfig;
        this.providerFactory = providerFactory;
    }

    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(QueryRequest request) {

        var cItem = appConfig.getCatalogueItem(request.getCatalogue(), request.getCatalogueItem());
        if (cItem == null) throw new NoSuchElementException(CATALOGUE_NOT_FOUND);

        if (request.getQueryArgs() != null && request.getQueryArgs().length > 0) {
            var query = String.format(cItem.getQuery(), request.getQueryArgs());
            cItem.setQuery(query);
        }
        request.setCatalogueItemInstance(cItem);

        System.out.println("CatalogueServiceImpl.queryCatalogueItem: " + request);

        var provider = providerFactory.getCatalogueProvider(request);
        return request.isSkipCache() ? getResponse(cItem, () -> provider.get().queryCatalogueItem(request))
                : getCachedResponse(cItem, () -> provider.get().queryCatalogueItem(request));

    }
    @Override
    public CompletableFuture<QueryResponse> queryCatalogueItem(String cacheKey) {
        var response = memoryCache.get(cacheKey);
        if(response == null) throw new NoSuchElementException(CATALOGUE_NOT_FOUND);
        response.setSource(CATALOGUE_SOURCE_CACHED);
        return CompletableFuture.completedFuture(response.clone());
    }

    @Override
    public void clearCatalogue(String catalogue, String catalogueItem) {
        var cItem = appConfig.getCatalogueItem(catalogue, catalogueItem);
        if (cItem == null) throw new NoSuchElementException(CATALOGUE_NOT_FOUND);

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
        var response = func.get().join();
        return CompletableFuture.completedFuture(response.clone());
    }

    public String getCacheKey(CatalogueItem catalogueItem) {
        return catalogueItem.getCacheKey();
    }

}

