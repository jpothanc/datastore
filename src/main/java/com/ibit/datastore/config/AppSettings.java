package com.ibit.datastore.config;

import com.ibit.datastore.helpers.CatalogueHelper;
import com.ibit.datastore.models.Catalogue;
import com.ibit.datastore.models.CatalogueItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Configuration
@Scope("singleton")

public class AppSettings {
    private Map<String, Catalogue> catalogue = new HashMap<>();

    public void setCatalogue(Map<String, Catalogue> catalogue) {
        this.catalogue = catalogue;
    }

    public Map<String, Catalogue> getCatalogue() {
        return catalogue;
    }

    private Map<String, CatalogueItem> catalogueItems = new HashMap<>();

    public Map<String, CatalogueItem> getCatalogueItems() {
        return catalogueItems;
    }

    public void setCatalogueItems(Map<String, CatalogueItem> catalogueItems) {
        this.catalogueItems = catalogueItems;
    }

    public AppSettings() {
//        var key = CatalogueHelper.getCatalogueKey("Trading","Users");
//        var catalogueItem = new CatalogueItem();
//        catalogueItem.setKey(key);
//        catalogueItem.setQuery("select * from users");
//        catalogueItem.setDatasource("ref");
//        catalogueItem.setPreload(false);
//
//        catalogueItems.put(key, catalogueItem);
        System.out.println("AppSettings init");
    }

    public CatalogueItem getCatalogueItem(String name, String itemName) throws NoSuchElementException {
        var key = CatalogueHelper.getCatalogueKey(name,itemName);

        name = name.toLowerCase();
        itemName = itemName.toLowerCase();
        if(!this.catalogue.containsKey(name))
            return null;

        var catalogue = this.catalogue.get(name);
        if(!catalogue.getItems().containsKey(itemName))
            return null;

        CatalogueItem clone = null;
        try {
            var cItem =  catalogue.getItems().get(itemName);
            if(cItem != null) {
                clone = (CatalogueItem)cItem.clone();
            }
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
