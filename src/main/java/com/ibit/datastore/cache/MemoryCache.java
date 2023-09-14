package com.ibit.datastore.cache;

import com.ibit.datastore.models.QueryResponse;

public interface  MemoryCache {
    void add(String key , QueryResponse value);
    QueryResponse get(String key);
}
