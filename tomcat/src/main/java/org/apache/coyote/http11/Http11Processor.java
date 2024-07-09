package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.*;
import camp.nextstep.model.User;
import camp.nextstep.model.UserNotFoundException;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String DEFAULT_MESSAGE = "Hello world!";
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
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream()) {

            final HttpRequest httpRequest = new HttpRequest(inputStream);
            final HttpResponse httpResponse = new HttpResponse(outputStream);
            final HttpPath path = httpRequest.getPath();
            if (path.isRoot()) {
                httpResponse.setContentType(ContentType.HTML);
                httpResponse.forwardBody(DEFAULT_MESSAGE);
                return;
            }

            if (path.isLoginPath()) {
                final QueryParameters parameters = httpRequest.getQueryParameters();
                final User user = InMemoryUserRepository.findByAccount(parameters.get(ACCOUNT))
                        .orElseThrow(UserNotFoundException::new);

                if (user.checkPassword(parameters.get(PASSWORD))) {
                    log.info("user : {}", user);
                } else {
                    httpResponse.sendRedirect("/401.html");
                    return;
                }
                httpResponse.sendRedirect("/index.html");
                return;
            }

            httpResponse.setContentType(ContentType.from(path));
            httpResponse.forward(path.getPath());
        } catch (final IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
