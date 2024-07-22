package org.apache.catalina.connector;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectorTest {
    @Test
    void 요청이_쓰레드수보다_많이들어올경우_큐에_쌓는다() throws InterruptedException {
        // given
        var connector = new Connector();
        connector.start();
        ExecutorService executorService = Executors.newFixedThreadPool(400);
        for (int i = 0; i < 125; i++) {
            executorService.execute(() -> {
                try {
                    testRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(100);
        }

        assertTrue(connector.getBlockingQueue().size() == 100);
    }

    private void testRequest() throws IOException {
        SocketAddress sockaddr = new InetSocketAddress("localhost", 8080);
        Socket s = new Socket();
        s.connect(sockaddr, 10000);

        final String httpRequest= String.join("\r\n",
                "GET /test HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        PrintWriter wtr = new PrintWriter(s.getOutputStream());
        wtr.println(httpRequest);
        wtr.flush();
    }
}