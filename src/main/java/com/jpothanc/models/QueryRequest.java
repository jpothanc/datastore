package com.jpothanc.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
