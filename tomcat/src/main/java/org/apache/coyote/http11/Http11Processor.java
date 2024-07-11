package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.RequestNotFoundException;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import camp.nextstep.request.HttpQueryParameters;
import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestParser;
import camp.nextstep.response.HttpResponse;
import org.apache.catalina.Session;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final SessionManager sessionManager;

    private final HttpRequestParser requestParser;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
        this.sessionManager = SessionManager.INSTANCE;

        this.requestParser = new HttpRequestParser();
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var inputStreamReader = new InputStreamReader(inputStream);
             final var bufferedReader = new BufferedReader(inputStreamReader);
             final var outputStream = connection.getOutputStream()
        ) {
            HttpRequest request = requestParser.parse(bufferedReader);
            HttpResponse response = new HttpResponse(request, sessionManager, outputStream);
            try {
                processRequest(request, response);
            } catch (RequestNotFoundException e) {
                response.render404();
                throw e;
            }
        } catch (IOException | UncheckedServletException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processRequest(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        String requestPath = request.getPath();

        if (requestPath.equals("/")) {
            processHelloWorld(response);
            return;
        }

        if (requestPath.equals("/login") && request.isGET()) {
            processGetLogin(request, response);
            return;
        }

        if (requestPath.equals("/login") && request.isPOST()) {
            processPostLogin(request, response);
            return;
        }

        if (requestPath.equals("/register") && request.isGET()) {
            processGetRegister(response);
            return;
        }

        if (requestPath.equals("/register") && request.isPOST()) {
            processPostRegister(request, response);
            return;
        }


        if (requestPath.equals("/index.html")) {
            processRenderStaticPage(request, response);
            log.debug("세션 조회: {}", request.getSession(sessionManager, false));
            return;
        }

        processRenderStaticPage(request, response);
    }

    private void processHelloWorld(HttpResponse response) throws IOException {
        response.renderText("Hello world!");
    }

    private void processGetLogin(HttpRequest request, HttpResponse response) throws IOException {
        if (isLoggedIn(request)) {
            response.redirectTo("/index.html");
            return;
        }
        response.renderStaticResource("/login.html");
    }

    // XXX: request 로
    private boolean isLoggedIn(HttpRequest request) throws IOException {
        Session session = request.getSession(sessionManager, false);
        if (session == null) return false;

        return session.getAttribute("user") != null;
    }

    private void processPostLogin(HttpRequest request, HttpResponse response) throws IOException {
        HttpQueryParameters requestBody = request.getRequestBody();
        String account = requireNonNull(requestBody.getString("account"));
        String password = requireNonNull(requestBody.getString("password"));

        Optional<User> user = InMemoryUserRepository.findByAccount(account);
        Boolean found = user
                .map(acc -> acc.checkPassword(password))
                .orElse(false);
        if (!found) {
            response.redirectTo("/401.html");
            return;
        }

        signInAs(user.get(), request);

        response.redirectTo("/index.html");
    }

    // XXX: request 로
    private void signInAs(User user, HttpRequest request) throws IOException {
        final var session = request.getSession(sessionManager, true);

        session.setAttribute("user", user);
        log.debug("로그인: {}", user);
    }

    private void processGetRegister(HttpResponse response) throws IOException {
        response.renderStaticResource("/register.html");
    }

    private void processPostRegister(HttpRequest request, HttpResponse response) throws IOException {
        final HttpQueryParameters requestBody = request.getRequestBody();

        final String account = requireNonNull(requestBody.getString("account"));
        final String email = requireNonNull(requestBody.getString("email"));
        final String password = requireNonNull(requestBody.getString("password"));

        User user = new User(account, password, email);
        InMemoryUserRepository.save(user);
        signInAs(user, request);
        log.debug("사용자 생성 후 로그인: {}", user);

        response.redirectTo("/index.html");
    }

    private void processRenderStaticPage(HttpRequest request, HttpResponse response) throws IOException {
        response.renderStaticResource(request.getPath());
    }
}
