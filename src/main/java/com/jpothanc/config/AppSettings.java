package com.jpothanc.config;

import com.jpothanc.helpers.CatalogueHelper;
import com.jpothanc.models.Catalogue;
import com.jpothanc.models.CatalogueItem;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@Configuration
public class AppSettings {
    private Map<String, Catalogue> catalogue = new HashMap<>();

    private Map<String, CatalogueItem> catalogueItems = new HashMap<>();

    public Map<String, Catalogue> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Map<String, Catalogue> catalogue) {
        this.catalogue = catalogue;
    }

    public AppSettings() {
        var key = CatalogueHelper.getCatalogueKey("Trading","Users");
        var catalogueItem = new CatalogueItem();
        catalogueItem.setKey(key);
        catalogueItem.setQuery("select * from users");
        catalogueItem.setDatasource("ref");
        catalogueItem.setPreload(false);

        catalogueItems.put(key, catalogueItem);
    }

    public CatalogueItem getCatalogueItem(String catalogue, String catalogueItem) throws NoSuchElementException {
        var key = CatalogueHelper.getCatalogueKey(catalogue,catalogueItem);
        return catalogueItems.get(key);
    }
}
