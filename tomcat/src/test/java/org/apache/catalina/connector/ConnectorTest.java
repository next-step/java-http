package org.apache.catalina.connector;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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


    public void urlConnectionReader() throws IOException {
        URL url = new URL("http://localhost:8080/index.html");
        URLConnection urlConn = url.openConnection();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConn.getInputStream()))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
        }
    }
}