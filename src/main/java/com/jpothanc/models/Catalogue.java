package com.jpothanc.models;

import java.util.HashMap;
import java.util.Map;

public class Catalogue {
    private Map<String,CatalogueItem> catalogueItem = new HashMap<>();
    public Map<String, CatalogueItem> getCatalogueItem() {
        return catalogueItem;
    }
    public void setCatalogueItem(Map<String, CatalogueItem> catalogueItem) {
        this.catalogueItem = catalogueItem;
    }
}
