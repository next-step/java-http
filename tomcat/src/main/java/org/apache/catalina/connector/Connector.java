package org.apache.catalina.connector;

import camp.nextstep.controller.ControllerRequestMapping;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.coyote.http11.Http11Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connector implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Connector.class);

    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_ACCEPT_COUNT = 100; // 요청 대기 QUEUE 사이즈(DEFAULT_THREAD_QUEUE_SIZE)
    private static final int DEFAULT_THREAD_POOL_SIZE = 150; // THREAD POOL 에 생성된 최소 THREAD 개수
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 250; // THREAD POOL 에 생성될 수 있는 최대 THREAD 개수

    private final ServerSocket serverSocket;
    private boolean stopped;
    private final ControllerRequestMapping requestMapping;
    private final ExecutorService executorService;

    public Connector(ControllerRequestMapping requestMapping) {
        this(DEFAULT_PORT, DEFAULT_ACCEPT_COUNT, requestMapping);
    }

    public Connector(final int port, final int acceptCount, final ControllerRequestMapping requestMapping) {
        this.serverSocket = createServerSocket(port, acceptCount); // 여기의 acceptCount는 소켓에 대한 queue라고 생각하면 된다.
        this.stopped = false;
        this.requestMapping = requestMapping;
        this.executorService = new ThreadPoolExecutor(DEFAULT_THREAD_POOL_SIZE,
            DEFAULT_MAXIMUM_POOL_SIZE, 100, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(DEFAULT_ACCEPT_COUNT));
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
        var processor = new Http11Processor(connection, requestMapping);
        this.executorService.execute(processor);
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
