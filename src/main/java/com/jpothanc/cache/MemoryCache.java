package com.jpothanc.cache;

import com.jpothanc.models.QueryResponse;

public interface  MemoryCache {
    void add(String key , QueryResponse value);
    QueryResponse get(String key);
}
