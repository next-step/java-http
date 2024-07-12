package org.apache.catalina.connector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
    private static final int DEFAULT_CORE_THREAD_POOL_SIZE = 250;
    private static final int DEFAULT_MAX_THREAD_POOL_SIZE = 250;
    private static final int DEFAULT_THREAD_QUEUE_CAPACITY = 100;

    public static ExecutorService defaultThreadPool() {
        return createThreadPool(
                DEFAULT_CORE_THREAD_POOL_SIZE,
                DEFAULT_MAX_THREAD_POOL_SIZE,
                DEFAULT_THREAD_QUEUE_CAPACITY
        );
    }

    public static ExecutorService createThreadPool(final int coreSize, final int maxSize, final int queueCapacity) {
        return new ThreadPoolExecutor(
                coreSize,
                maxSize,
                0L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueCapacity)
        );
    }
}
