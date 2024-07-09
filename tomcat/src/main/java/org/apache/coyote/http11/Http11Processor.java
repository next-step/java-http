package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

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
                final URL resource = getClass().getClassLoader().getResource("static/index.html");
                final var response = String.join("\r\n",
                        "HTTP/1.1 200 OK ",
                        "Content-Type: text/html;charset=utf-8 ",
                        "Content-Length: 5564 ", // 운영체제 환경에 따라 다른 값이 나올 수 있음. 자신의 개발 환경에 맞춰 수정할 것.
                        "",
                        new String(Files.readAllBytes(new File(resource.getFile()).toPath())));
                outputStream.write(response.getBytes());
                outputStream.flush();
                return;
            }

            final var responseBody = "Hello world!";

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: text/html;charset=utf-8 ",
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }


    public String getRequestLine(InputStream is) {
        final var br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        final var lines = br.lines().toList();
        if (!lines.isEmpty()) {
            return lines.get(0);
        }
        throw new UncheckedServletException(new RuntimeException("Request is empty"));
    }
}
