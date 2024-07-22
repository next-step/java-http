package org.apache.catalina.connector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.coyote.http11.Http11Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Connector.class);

    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_ACCEPT_COUNT = 100;
    public static final int DEFAULT_THREAD_CORE_POOL_SIZE = 200; // 기본적으로 요청을 처리할 thread 값
    public static final int DEFAULT_THREAD_MAXIMUM_POOL_SIZE = 250; // 요청을 처리할 thread 최대 값
    public static final long DEFAULT_THREAD_KEEP_ALIVE_TIME = 0L; // core thread 수 보다 많은 thread 가 생성되는 경우 추가된 thread 를 정리하기 위한 대기 시간
    public static final int DEFAULT_THREAD_QUEUE_CAPACITY = 100; // core thread 수 이상의 요청일 들어올 때 대기하게 되는 queue 의 capacity

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private boolean stopped;

    public Connector() {
        this(DEFAULT_PORT, DEFAULT_ACCEPT_COUNT);
    }

    public Connector(final int port, final int acceptCount) {
        this.serverSocket = createServerSocket(port, acceptCount);
        this.executorService = new ThreadPoolExecutor(
            DEFAULT_THREAD_CORE_POOL_SIZE,
            DEFAULT_THREAD_MAXIMUM_POOL_SIZE,
            DEFAULT_THREAD_KEEP_ALIVE_TIME,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(DEFAULT_THREAD_QUEUE_CAPACITY)
        );
        this.stopped = false;
    }

    /* 기본 thread 풀 설정을 사용하고 싶지 않을 때 커스텀 할 수 있는 생성자 */
    public Connector(final int port, final int acceptCount, final int maxThreads) {
        this.serverSocket = createServerSocket(port, acceptCount);
        this.executorService = new ThreadPoolExecutor(
            DEFAULT_THREAD_CORE_POOL_SIZE,
            maxThreads, // 최대로 요청을 처리할 수 있는 thread 수
            DEFAULT_THREAD_KEEP_ALIVE_TIME,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(acceptCount) // thread 를 대기시킬 queue 사용량
        );
    }

    private ServerSocket createServerSocket(final int port, final int acceptCount) {
        try {
            final int checkedPort = checkPort(port);
            final int checkedAcceptCount = checkAcceptCount(acceptCount);
            return new ServerSocket(checkedPort, checkedAcceptCount);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void start() {
        var thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        stopped = false;
        log.info("Web Application Server started {} port.", serverSocket.getLocalPort());
    }

    @Override
    public void run() {
        // 클라이언트가 연결될때까지 대기한다.
        while (!stopped) {
            connect();
        }
    }

    private void connect() {
        try {
            process(serverSocket.accept());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void process(final Socket connection) {
        if (connection == null) {
            return;
        }
        var processor = new Http11Processor(connection);
        executorService.execute(processor);
    }

    public void stop() {
        stopped = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private int checkPort(final int port) {
        final var MIN_PORT = 1;
        final var MAX_PORT = 65535;

        if (port < MIN_PORT || MAX_PORT < port) {
            return DEFAULT_PORT;
        }
        return port;
    }

    private int checkAcceptCount(final int acceptCount) {
        return Math.max(acceptCount, DEFAULT_ACCEPT_COUNT);
    }
}
