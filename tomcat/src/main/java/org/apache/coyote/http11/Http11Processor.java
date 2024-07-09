package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             OutputStream outputStream = connection.getOutputStream()) {

            RequestLine requestLine = RequestLine.from(br.readLine());
            String path = requestLine.getPath();

            File resource = new ResourceFinder().findByPath(path);
            MediaType mediaType = MediaType.from(resource);
            String responseBody = new String(Files.readAllBytes(resource.toPath()));

            String response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: %s;charset=utf-8 ".formatted(mediaType.getValue()),
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
