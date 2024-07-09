package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.*;
import camp.nextstep.model.User;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String DEFAULT_MESSAGE = "Hello world!";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

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

            if (httpRequest.isSessionEmpty()) {
                httpResponse.setSession(UUID.randomUUID().toString());
            }

            final HttpPath path = httpRequest.getPath();
            if (path.isRoot()) {
                processRoot(httpResponse);
                return;
            }

            if (path.isLoginPath()) {
                processLogin(httpRequest, httpResponse);
                return;
            }

            if (path.isRegisterPath()) {
                processRegister(httpRequest, httpResponse);
                return;
            }

            httpResponse.setContentType(ContentType.from(path));
            httpResponse.forward(path.getPath());
        } catch (final IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processRoot(final HttpResponse httpResponse) throws IOException {
        httpResponse.setContentType(ContentType.HTML);
        httpResponse.forwardBody(DEFAULT_MESSAGE);
    }

    private void processLogin(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        if (httpRequest.isGetMethod()) {
            httpResponse.setContentType(ContentType.HTML);
            httpResponse.forward("/login.html");
            return;
        }

        if (httpRequest.isPostMethod()) {
            final RequestBody requestBody = httpRequest.getRequestBody();
            final Optional<User> userOptional = InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT));

            if (userOptional.isEmpty()) {
                httpResponse.sendRedirect("/401.html");
                return;
            }

            final User user = userOptional.get();
            if (user.checkPassword(requestBody.get(PASSWORD))) {
                log.info("user : {}", user);
                final HttpSession httpSession = httpRequest.getSession(true);
                httpSession.setAttribute("user", user);
                httpResponse.setSession(httpSession.getId());
                httpResponse.sendRedirect("/index.html");
                return;
            }
            httpResponse.sendRedirect("/401.html");
        }
    }

    private void processRegister(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        if (httpRequest.isGetMethod()) {
            httpResponse.setContentType(ContentType.HTML);
            httpResponse.forward("/register.html");
            return;
        }
        if (httpRequest.isPostMethod()) {
            final RequestBody requestBody = httpRequest.getRequestBody();
            InMemoryUserRepository.save(
                    new User(requestBody.get(ACCOUNT), requestBody.get(PASSWORD), requestBody.get(EMAIL))
            );
            httpResponse.sendRedirect("/index.html");
        }
    }
}
