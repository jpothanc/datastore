package com.ibit.datastore.models;

import com.ibit.datastore.config.AppSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
public class QueryRequest {
    private String catalogue;
    private String catalogueItem;
    private String query;
    private boolean skipCache;
    private boolean cancel;
    private String clientIdentifier;
    private String[] queryArgs;
    private CatalogueItem catalogueItemInstance;
}
