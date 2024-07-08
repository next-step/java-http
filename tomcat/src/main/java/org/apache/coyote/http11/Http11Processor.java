package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String ROOT_PATH_BODY = "Hello world!";
    private static final String LOGIN_PATH = "/login";
    private static final String STATIC_PATH = "static";

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
        try (final var br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             final var outputStream = connection.getOutputStream()) {
            RequestLine requestLine = RequestLine.from(br.readLine());
            HttpPath path = requestLine.getPath();
            String responseBody = getResponseBody(path);

            String response = String.join("\r\n",
                                   "HTTP/1.1 200 OK ",
                                   "Content-Type: " + ContentType.from(requestLine.getExtension()).getValue() + " ",
                                   "Content-Length: " + responseBody.getBytes().length + " ",
                                   "",
                                   responseBody);

            if (path.equals(LOGIN_PATH)) {
                Map<String, Object> parameters = requestLine.getParameters();
                User user = InMemoryUserRepository.findByAccount(parameters.get("account").toString())
                                                  .orElseThrow(IllegalArgumentException::new);
                log.info(user.toString());
            }

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getResponseBody(HttpPath path) throws IOException {
        if (path.isRootPath()) {
            return ROOT_PATH_BODY;
        }
        StringBuilder pathBuilder = new StringBuilder(STATIC_PATH);
        pathBuilder.append(path.getPath());

        if (path.getExtension().equals(StringUtils.EMPTY)) {
            pathBuilder.append(ContentType.HTML.getExtension());
        }

        return Files.readString(Path.of(ClassLoader.getSystemResource(pathBuilder.toString()).getFile()));
    }
}
