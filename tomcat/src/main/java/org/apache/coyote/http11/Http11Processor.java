package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.*;
import camp.nextstep.model.User;
import camp.nextstep.model.UserNotFoundException;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String DEFAULT_MESSAGE = "Hello world!";
    private static final String PATH_PREFIX = "static";
    private static final String NOT_FOUND_PATH = "/404.html";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";

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
             final var outputStream = new BufferedOutputStream(connection.getOutputStream())) {

            final RequestLine requestLine = new RequestLine(inputStreamReader.readLine());
            final HttpPath path = requestLine.getPath();

            if (path.isRoot()) {
                final var responseBody = DEFAULT_MESSAGE;
                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(ContentType.HTML);
                headers.setContentLength(responseBody.getBytes().length);

                final var responseHeader = String.join(System.lineSeparator(),
                        StatusLine.createOk().convertToString(),
                        headers.convertToString(),
                        "",
                        responseBody);

                outputStream.write(responseHeader.getBytes());
                outputStream.flush();
                return;
            }

            if (path.isLoginPath()) {
                try {
                    processLogin(requestLine);
                    final HttpHeaders headers = new HttpHeaders();
                    headers.add("Location", "/index.html");
                    final var responseHeader = String.join(System.lineSeparator(),
                            StatusLine.createFound().convertToString(),
                            headers.convertToString(),
                            System.lineSeparator());
                    outputStream.write(responseHeader.getBytes());
                } catch (final UserNotFoundException e) {
                    final HttpHeaders headers = new HttpHeaders();
                    headers.add("Location", "/401.html");
                    final var responseHeader = String.join(System.lineSeparator(),
                            StatusLine.createFound().convertToString(),
                            headers.convertToString(),
                            System.lineSeparator());
                    outputStream.write(responseHeader.getBytes());
                }
                return;
            }

            final var resource = getResource(path);
            final byte[] resourceBytes = resource.readAllBytes();
            final var responseBody = new String(resourceBytes);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(ContentType.from(path));
            headers.setContentLength(resourceBytes.length);

            final var responseHeader = String.join(System.lineSeparator(),
                    StatusLine.createOk().convertToString(),
                    headers.convertToString(),
                    "",
                    responseBody);

            outputStream.write(responseHeader.getBytes());
            outputStream.flush();
        } catch (final IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processLogin(final RequestLine requestLine) {
        final QueryParameters parameters = requestLine.getQueryParameters();
        final User user = InMemoryUserRepository.findByAccount(parameters.get(ACCOUNT))
                .orElseThrow(UserNotFoundException::new);

        if (user.checkPassword(parameters.get(PASSWORD))) {
            log.info("user : {}", user);
            return;
        }
        throw new UserNotFoundException();
    }

    private Resource getResource(final HttpPath path) throws IOException {
        final Resource resource = new Resource(PATH_PREFIX + path.getPath());
        if (resource.exists()) {
            return resource;
        }
        return new Resource(PATH_PREFIX + NOT_FOUND_PATH);
    }
}
