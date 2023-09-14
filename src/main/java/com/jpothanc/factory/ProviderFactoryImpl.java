package com.jpothanc.factory;

import com.jpothanc.models.Enums;
import com.jpothanc.models.QueryRequest;
import com.jpothanc.services.CatalogueProvider;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.jpothanc.helpers.CatalogueHelper.getCatalogueKey;

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

        var p = providers.stream().filter(x -> x.getName().toString() == Enums.CatalogueProviders.Database.toString());
        return p.findFirst();
    }

    private Optional<CatalogueProvider> getCatalogueProviderInternal(QueryRequest request) {
        var key = getCatalogueKey(request);
        var provider = providers.stream().filter(x->x.getName().toString() == key);
        return provider.findFirst();
    }
}
