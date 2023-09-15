package com.ibit.datastore.config;

import com.ibit.common.database.models.DatasourceSetting;
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
@Getter
@Setter
public class AppSettings {

    private Map<String, Catalogue> catalogues = new HashMap<>();
    private Map<String, DatasourceSetting> dataSources = new HashMap<>();

    public AppSettings() {
        System.out.println("AppSettings init");
    }
    public CatalogueItem getCatalogueItem(String name, String itemName) throws NoSuchElementException {
        var key = CatalogueHelper.getCatalogueKey(name,itemName);

        name = name.toLowerCase();
        itemName = itemName.toLowerCase();
        if(!this.catalogues.containsKey(name))
            return null;

        var catalogue = this.catalogues.get(name);
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
    public Optional<DatasourceSetting> getDataSourceSetting(String datasource) {
        var setting =  getDataSources().containsKey(datasource) ?
                getDataSources().get(datasource) :
                null;
        return Optional.ofNullable(setting);
    }
}
