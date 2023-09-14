package com.ibit.datastore.factory;

import com.ibit.datastore.services.CatalogueProvider;
import com.ibit.datastore.models.QueryRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.ibit.datastore.helpers.CatalogueHelper.getCatalogueKey;
import static com.ibit.datastore.models.Enums.CatalogueProviders.*;

@Component
public class ProviderFactoryImpl implements ProviderFactory{

    List<CatalogueProvider> providers;
    public ProviderFactoryImpl(List<CatalogueProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Optional<CatalogueProvider> getCatalogueProvider(QueryRequest request) {
        var key = getCatalogueKey(request);
        var provider = getCatalogueProviderInternal(request);
        if(provider.isPresent())
            return provider;

        return providers.stream().filter(x -> x.getName().toString() == Database.toString()).findFirst();
    }

    private Optional<CatalogueProvider> getCatalogueProviderInternal(QueryRequest request) {
        var key = getCatalogueKey(request);
        var provider = providers.stream().filter(x->x.getName().toString() == key);
        return provider.findFirst();
    }
}
