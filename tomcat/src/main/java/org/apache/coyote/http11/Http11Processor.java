package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.HttpPath;
import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.RequestURI;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStreamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             final var outputStream = connection.getOutputStream()) {

            final RequestLine requestLine = new RequestLine(inputStreamReader.readLine());

            final RequestURI requestURI = requestLine.getRequestURI();

            var responseBody = "Hello world!".getBytes();
            var responseContentType = "text/html";
            final HttpPath path = requestURI.getPath();
            if (path.isHtml()) {
                InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("static" + path.getPath());
                if (inputStream == null) {
                    inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("static/404.html");
                }
                responseBody = inputStream.readAllBytes();
            } else if (path.isCss()) {
                InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("static" + path.getPath());
                responseBody = inputStream.readAllBytes();
                responseContentType = "text/css";
            }

            final var responseHeader = String.join(System.lineSeparator(),
                    "HTTP/1.1 200 OK ",
                    String.format("Content-Type: %s;charset=utf-8 ", responseContentType),
                    "Content-Length: " + responseBody.length + " ",
                    System.lineSeparator());

            outputStream.write(responseHeader.getBytes());
            outputStream.write(responseBody);
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
