package com.jpothanc.factory;

import com.jpothanc.models.QueryRequest;
import com.jpothanc.services.CatalogueProvider;

import java.util.Optional;


public interface ProviderFactory {
    Optional<CatalogueProvider> getCatalogueProvider(QueryRequest request);
}
