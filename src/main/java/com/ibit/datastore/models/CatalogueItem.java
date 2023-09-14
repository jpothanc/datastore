package com.ibit.datastore.models;


import com.ibit.datastore.helpers.CatalogueHelper;
import com.ibit.datastore.helpers.Constants;

import java.util.Objects;

public class CatalogueItem implements Cloneable{
    private String key;
    private String datasource;
    private String query;
    private boolean preload;
    private String indexes;
    private String health;

    private String[] queryArgs;
    @Override
    public int hashCode() {
        return Objects.hash(key, query,datasource);
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getCacheKey(String type){
        return new StringBuilder(key).append(type).append(hashCode()).toString();
    }
    public String getCacheKey(){

        //return new StringBuilder(key).append(QUERY_TYPE_REST).append(hashCode()).toString();
        return CatalogueHelper.generateCacheKey(key, Constants.QUERY_TYPE_REST,hashCode());
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

    public String[] getQueryArgs() {
        return queryArgs;
    }

    public void setQueryArgs(String[] queryArgs) {
        this.queryArgs = queryArgs;
    }
}
