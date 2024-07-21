package org.apache.catalina.connector;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class ConnectorTest {

    private static ExecutorService es = Executors.newFixedThreadPool(10);

    @Test
    @DisplayName("Connector를 실행하고 URLConnection을 통해 응답을 받는다")
    public void startTest() throws IOException {
        Connector con = new Connector(8080, 1);
        con.start();

        es.execute(con::run);

        Assertions.assertThatNoException()
                        .isThrownBy(this::urlConnectionReader);
    }

    @Test
    @DisplayName("동시 request 처리 테스트")
    public void executorStudyTest() throws InterruptedException {
        Connector con = new Connector(8080, 100);
        new Thread(() -> {
            con.start();
            con.run();
        }).start();

        final int concurrentRequest = 50;
        CountDownLatch latch = new CountDownLatch(concurrentRequest);
        ExecutorService es = Executors.newFixedThreadPool(concurrentRequest);
        for (int i = 0; i < concurrentRequest; i++) {
            es.submit(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        this.urlConnectionReader();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                latch.countDown();
            });

        }
        latch.await();
    }



    public void urlConnectionReader() throws IOException {
        URL url = new URL("http://localhost:8080/index.html");
        URLConnection urlConn = url.openConnection();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConn.getInputStream()))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(LocalDateTime.now() );
        }
    }
}