package com.jpothanc.cache;

import com.jpothanc.cache.BlockingCache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class BlockingCacheImpl<T> implements BlockingCache<T> {
    private BlockingQueue<T> blockingQueue;

    public BlockingCacheImpl(Integer capacity) {
        this.blockingQueue = new ArrayBlockingQueue<>(capacity);
    }

    @Override
    public void add(T item) {
        blockingQueue.add(item);
    }

    @Override
    public void run(Consumer<T> handler) {
        while (true)
        {
            try {
                var item = blockingQueue.take();
                handler.accept(item);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
