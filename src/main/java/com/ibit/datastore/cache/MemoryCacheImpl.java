package com.ibit.datastore.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ibit.datastore.models.QueryResponse;
import org.springframework.stereotype.Service;

@Service
public class MemoryCacheImpl implements MemoryCache{
    Cache<String, QueryResponse> cache;

    public MemoryCacheImpl() {
        cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .build();
    }

    @Override
    public void add(String key, QueryResponse value) {
        cache.put(key, value);
    }

    @Override
    public QueryResponse get(String key) {
        return cache.getIfPresent(key);
    }
}
