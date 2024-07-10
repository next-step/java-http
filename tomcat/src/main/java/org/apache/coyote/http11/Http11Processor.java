package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream()) {

            var requestLine = RequestParser.parse(inputStream, StandardCharsets.UTF_8);
            if (requestLine.pathEndsWith("/index.html")) {
                byte[] readFile = FileLoader.read("static/index.html");
                var response = new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, StandardCharsets.UTF_8, readFile);
                outputStream.write(response.generateMessage().getBytes());
                outputStream.flush();
                return;
            }

            final var responseBody = "Hello world!";

            var response2 = new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, StandardCharsets.UTF_8, responseBody.getBytes());

            outputStream.write(response2.generateMessage().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

}
