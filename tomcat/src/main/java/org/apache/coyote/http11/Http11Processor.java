package org.apache.coyote.http11;

import camp.nextstep.RequestParser;
import camp.nextstep.UserService;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.dto.RequestLine;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private final UserService userService;

    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.userService = new UserService();
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

            RequestLine requestLine = RequestParser.parseRequest(inputStream);
            String filePath = requestLine.getPath();
            String fileExtension = requestLine.getFileExtension();
            if (StringUtils.isEmpty(fileExtension)) {
                fileExtension = "html";
                filePath = "%s.%s".formatted(filePath, fileExtension);
            }
            final URL resourceUrl = getClass().getClassLoader().getResource("static" + filePath);
            final var responseBody = Files.readAllBytes(new File(resourceUrl.getFile()).toPath());

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: text/%s;charset=utf-8 ".formatted(fileExtension),
                    "Content-Length: " + responseBody.length + " ",
                    "",
                    new String(responseBody));

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
