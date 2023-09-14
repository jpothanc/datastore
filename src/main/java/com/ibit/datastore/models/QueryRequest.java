package com.ibit.datastore.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryRequest {
    private String catalogue;
    private String catalogueItem;
    private String query;
    private boolean skipCache;
    private boolean cancel;
    private String clientIdentifier;
}
