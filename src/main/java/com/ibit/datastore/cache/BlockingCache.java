package com.ibit.datastore.cache;

import java.util.function.Consumer;

public interface BlockingCache<T> {
    void add(T item);

    void run(Consumer<T> handler);
}
