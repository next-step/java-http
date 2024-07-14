package org.apache.catalina.connector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {
    // 동시에 수행할 수 있는 Thread 수
    private static final int DEFAULT_CORE_THREAD_POOL_SIZE = 250;

    // 최대 수행할 수 있는 Thread 수
    private static final int DEFAULT_MAX_THREAD_POOL_SIZE = 250;

    // 실행가능한 Thread Pool이 없을 경우 Thread를 대기하기 위한 Queue 사이즈
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
