package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import jakarta.servlet.ServletContext;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

            final Http11Input http11Input = new Http11Input(inputStream);
            http11Input.parseRequestLine();

            final Request request = http11Input.getRequest();

            var responseBody = "Hello world!";
            // TODO 일급컬렉션 변경
            final Map<String, String> responseHeaderMap = new HashMap<>();
            responseHeaderMap.put("Content-Type", "text/html;charset=utf-8 ");

            // TODO 역할 분리
            final URL resource = getClass().getClassLoader().getResource("static/" + request.getPath());

            if (resource != null) {
                final Path path = new File(resource.getFile()).toPath();
                responseBody = new String(Files.readAllBytes(path));
                final String mimeType = Files.probeContentType(path);
                responseHeaderMap.put("Content-Type", mimeType + ";charset=utf-8 ");
            }

            final String headers = responseHeaderMap.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining("\r\n"));

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    headers,
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
