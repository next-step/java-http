package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UserNotFoundException;
import camp.nextstep.model.User;
import org.apache.catalina.Session;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

public class RequestHandler {

    private static final String INDEX_PATH = "/index.html";
    private static final String UNAUTHORIZED_PATH = "/401.html";
    private static final String NOT_FOUND_PATH = "/404.html";
    private static final String JSESSIONID = "JSESSIONID";

    private final Session session;

    private RequestHandler(final Session session) {
        this.session = session;
    }

    public static RequestHandler from(final Session session) {
        return new RequestHandler(session);
    }

    public void handle(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        String path = httpRequest.getPath();
        if (path.contains("/login") && httpRequest.isPost()) {
            handleLogin(httpRequest, httpResponse);
            return;
        }

        if (path.contains("/login") && httpRequest.isGet()) {
            Cookie cookie = httpRequest.getCookie(JSESSIONID);
            if (cookie.isNotEmpty() && Objects.nonNull(session.getAttribute(cookie.getValue()))) {
                httpResponse.sendRedirect(INDEX_PATH);
                return;
            }
        }

        if (path.contains("/register") && httpRequest.isPost()) {
            handleRegister(httpRequest, httpResponse);
            return;
        }

        if (httpRequest.isGet()) {
            handleResourceRequiredRequest(httpRequest, httpResponse);
            return;
        }

        httpResponse.sendRedirect(NOT_FOUND_PATH);
    }

    private void handleLogin(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String account = httpRequest.getBodyValue("account");
        User user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new UserNotFoundException(account));
        String password = httpRequest.getBodyValue("password");

        if (user.checkPassword(password)) {
            String uuid = UUID.randomUUID().toString();
            session.setAttribute(uuid, user);

            Cookie cookie = Cookie.of(JSESSIONID, uuid);
            httpResponse.setCookie(cookie);
            httpResponse.sendRedirect(INDEX_PATH);
            return;
        }

        httpResponse.sendRedirect(UNAUTHORIZED_PATH);
    }

    private void handleRegister(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String account = httpRequest.getBodyValue("account");
        String password = httpRequest.getBodyValue("password");
        String email = httpRequest.getBodyValue("email");

        User user = new User(InMemoryUserRepository.getAutoIncrement(), account, password, email);
        InMemoryUserRepository.save(user);
        httpResponse.sendRedirect(INDEX_PATH);
    }

    private void handleResourceRequiredRequest(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        File resource = new ResourceFinder().findByPath(httpRequest.getPath());
        MediaType mediaType = MediaType.from(resource);
        HttpHeader header = HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getValue(), mediaType.getValue() + ";charset=utf-8");
        String responseBody = new String(Files.readAllBytes(resource.toPath()));

        httpResponse.addHeader(header);
        httpResponse.setBody(responseBody);
    }

    public Session getSession() {
        return session;
    }
}
