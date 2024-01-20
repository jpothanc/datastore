package com.ibit.datastore.cache;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
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
    public void subscribe(Consumer<T> handler) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            while (true) {
                try {
                    System.out.println("Waiting for item");
                    var item = blockingQueue.take();
                    handler.accept(item);
                    Thread.sleep(1);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
