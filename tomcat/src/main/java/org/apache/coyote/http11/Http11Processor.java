package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.StringJoiner;
import java.util.UUID;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private static final String CRLF = "\r\n";
    private static final String INDEX_PATH = "/index.html";
    private static final String UNAUTHORIZED_PATH = "/401.html";
    private static final String NOT_FOUND_PATH = "/404.html";
    private static final String JSESSIONID = "JSESSIONID";

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
            HttpRequest httpRequest = parseHttpRequest(br);
            HttpResponse httpResponse = HttpResponse.from(httpRequest.getProtocol());
            handleRequest(httpRequest, httpResponse);

            outputStream.write(httpResponse.createFormat().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private HttpRequest parseHttpRequest(final BufferedReader br) throws IOException {
        String httpRequestMessage = readHttpRequestMessage(br);
        HttpRequest httpRequest = HttpRequest.from(httpRequestMessage);
        handleRequestBody(httpRequest, br);
        return httpRequest;
    }

    private String readHttpRequestMessage(final BufferedReader br) throws IOException {
        StringJoiner stringJoiner = new StringJoiner(CRLF);
        while (true) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            stringJoiner.add(line);
        }

        return stringJoiner.toString();
    }

    private void handleRequestBody(final HttpRequest httpRequest, final BufferedReader br) throws IOException {
        if (httpRequest.isPost()) {
            String requestBody = readRequestBody(br, httpRequest);
            httpRequest.addRequestBody(requestBody);
        }
    }

    private String readRequestBody(final BufferedReader br, final HttpRequest httpRequest) throws IOException {
        long contentLength = getContentLength(httpRequest);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            int read = br.read();
            sb.append((char) read);
        }

        return sb.toString();
    }

    private long getContentLength(final HttpRequest httpRequest) {
        String headerValue = httpRequest.getHeaderValue(HttpHeaderName.CONTENT_LENGTH.getValue());
        if (headerValue == null || headerValue.isBlank()) {
            return 0;
        }
        return Long.parseLong(headerValue);
    }

    //TODO: SessionManager, Session, RequestHandler, Controller 
    private void handleRequest(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        String path = httpRequest.getPath();
        if (path.contains("/login") && httpRequest.isPost()) {
            handleLogin(httpRequest, httpResponse);
            return;
        }

        if (path.contains("/login") && httpRequest.isGet()) {
            Cookie cookie = httpRequest.getCookie(JSESSIONID);
            if (cookie.isNotEmpty() && InMemorySessionRepository.exists(cookie.getValue())) {
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
        User user = InMemoryUserRepository.findByAccount(httpRequest.getBodyValue("account"))
                .orElseThrow();
        String password = httpRequest.getBodyValue("password");

        if (user.checkPassword(password)) {
            String uuid = UUID.randomUUID().toString();
            InMemorySessionRepository.save(uuid, user);

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
}
