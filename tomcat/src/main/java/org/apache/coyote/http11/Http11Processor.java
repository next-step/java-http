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
import java.util.List;
import java.util.StringJoiner;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private static final String CRLF = "\r\n";
    private static final String INDEX_PAGE = "/index.html";
    private static final String UNAUTHORIZED_PAGE = "/401.html";
    private static final String NOT_FOUND_PAGE = "/404.html";
    private static final String EMPTY = "";

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
            HttpResponse response = handleRequest(httpRequest);
            outputStream.write(response.createFormat().getBytes());
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

    private HttpResponse handleRequest(final HttpRequest httpRequest) throws IOException {
        String path = httpRequest.getPath();
        if (path.contains("/login") && httpRequest.isPost()) {
            String location = handleLogin(httpRequest);
            List<HttpHeader> httpHeaders = List.of(HttpHeader.of(HttpHeaderName.LOCATION.getValue(), location));
            return HttpResponse.of(httpRequest.getProtocol(), HttpStatus.FOUND, httpHeaders, EMPTY);
        }

        if (path.contains("/register") && httpRequest.isPost()) {
            handleRegister(httpRequest);
            List<HttpHeader> httpHeaders = List.of(HttpHeader.of(HttpHeaderName.LOCATION.getValue(), INDEX_PAGE));
            return HttpResponse.of(httpRequest.getProtocol(), HttpStatus.FOUND, httpHeaders, EMPTY);
        }

        if (httpRequest.isGet()) {
            return handleGetMethod(httpRequest, path);
        }

        List<HttpHeader> httpHeaders = List.of(HttpHeader.of(HttpHeaderName.LOCATION.getValue(), NOT_FOUND_PAGE));
        return HttpResponse.of((httpRequest.getProtocol()), HttpStatus.FOUND, httpHeaders, EMPTY);
    }


    private String handleLogin(final HttpRequest httpRequest) {
        User user = InMemoryUserRepository.findByAccount(httpRequest.getBodyValue("account"))
                .orElseThrow();
        String password = httpRequest.getBodyValue("password");

        if (user.checkPassword(password)) {
            return INDEX_PAGE;
        }

        return UNAUTHORIZED_PAGE;
    }

    private void handleRegister(final HttpRequest httpRequest) {
        String account = httpRequest.getBodyValue("account");
        String password = httpRequest.getBodyValue("password");
        String email = httpRequest.getBodyValue("email");

        User user = new User(InMemoryUserRepository.getAutoIncrement(), account, password, email);
        InMemoryUserRepository.save(user);
    }

    private HttpResponse handleGetMethod(final HttpRequest httpRequest, final String path) throws IOException {
        File resource = new ResourceFinder().findByPath(path);
        MediaType mediaType = MediaType.from(resource);
        List<HttpHeader> httpHeaders = List.of(HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getValue(), mediaType.getValue() + ";charset=utf-8"));
        String responseBody = new String(Files.readAllBytes(resource.toPath()));
        return HttpResponse.of(httpRequest.getProtocol(), HttpStatus.OK, httpHeaders, responseBody);
    }
}
