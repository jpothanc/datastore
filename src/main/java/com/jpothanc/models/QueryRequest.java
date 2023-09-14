package com.jpothanc.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryRequest {
    private String catalogue;
    private String catalogueItem;
    private boolean skipCache;
    private boolean cancel;
}
