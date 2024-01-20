package com.ibit.datastore.cache;

import java.util.function.Consumer;

public interface BlockingCache<T> {
    void add(T item);

    void subscribe(Consumer<T> handler);
}
