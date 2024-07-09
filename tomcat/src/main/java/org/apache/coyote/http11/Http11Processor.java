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
import java.util.Optional;
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

            if (isLoginPath(path)) {
                handleLogin(requestLine);
            }

            Response response = getResponseBody(path)
                .map(responseBody -> Response.ok(
                    ContentType.from(path.getExtension()),
                    responseBody
                ))
                .orElse(Response.notFound());

            outputStream.write(response.toHttp11());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean isLoginPath(HttpPath path) {
        return LOGIN_PATH.equals(path.getPath());
    }

    private void handleLogin(RequestLine requestLine) {
        Map<String, Object> parameters = requestLine.getParameters();
        User user = InMemoryUserRepository.findByAccount(parameters.get("account").toString())
            .orElseThrow(IllegalArgumentException::new);

        user.checkPassword(parameters.get("password").toString());
        log.info(user.toString());
    }

    private Optional<String> getResponseBody(HttpPath path) throws IOException {
        if (path.isRootPath()) {
            return Optional.of(ROOT_PATH_BODY);
        }
        File file = new File(ClassLoader.getSystemResource(STATIC_PATH).getPath() + path.getFilePath());
        if (!file.exists()) {
            return Optional.empty();
        }
        return Optional.of(Files.readString(file.toPath()));
    }
}
