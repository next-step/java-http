package org.apache.coyote.http11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestLineParser {

    private final Socket connection;

    public RequestLineParser(final Socket connection) {
        this.connection = connection;
    }

    public RequestLine parse() {
        try (final var inputStream = connection.getInputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String requestLine = br.readLine();
            return RequestLine.from(requestLine);
        } catch (IOException e) {
            return null;
        }
    }
}
