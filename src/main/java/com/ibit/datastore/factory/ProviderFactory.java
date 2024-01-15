package com.ibit.datastore.factory;

import com.ibit.datastore.models.QueryRequest;
import com.ibit.datastore.services.providers.CatalogueProvider;

import java.util.Optional;


public interface ProviderFactory {
    Optional<CatalogueProvider> getCatalogueProvider(QueryRequest request);
}
