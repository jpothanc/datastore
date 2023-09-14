package com.jpothanc.models;


import com.jpothanc.helpers.CatalogueHelper;

import java.util.Objects;

import static com.jpothanc.helpers.Constants.QUERY_TYPE_REST;

public class CatalogueItem {
    private String key;
    private String datasource;
    private String query;
    private boolean preload;
    private String indexes;
    private String health;
    @Override
    public int hashCode() {
        return Objects.hash(key, query,datasource);
    }

    public String getCacheKey(String type){
        return new StringBuilder(key).append(type).append(hashCode()).toString();
    }
    public String getCacheKey(){

        //return new StringBuilder(key).append(QUERY_TYPE_REST).append(hashCode()).toString();
        return CatalogueHelper.generateCacheKey(key,QUERY_TYPE_REST,hashCode());
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isPreload() {
        return preload;
    }

    public void setPreload(boolean preload) {
        this.preload = preload;
    }

    public String getIndexes() {
        return indexes;
    }

    public void setIndexes(String indexes) {
        this.indexes = indexes;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
