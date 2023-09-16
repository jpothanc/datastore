package com.ibit.datastore.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibit.datastore.config.AppConfig;
import com.ibit.datastore.helpers.CatalogueHelper;
import com.ibit.datastore.helpers.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class CatalogueItem implements Cloneable {

    @Autowired
    AppConfig appConfig;

    private String key;
    private String datasource;
    private String query;
    private boolean preload;
    private String indexes;
    private String health;

    @Override
    public int hashCode() {
        return Objects.hash(key, query, datasource);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getCacheKey(String type) {
        return new StringBuilder(key).append(type).append(hashCode()).toString();
    }

    public String getCacheKey() {
        return CatalogueHelper.generateCacheKey(key, Constants.QUERY_TYPE_REST, hashCode());
    }


}
