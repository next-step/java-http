package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.*;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import camp.nextstep.util.FileUtil;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";
    private static final String ROOT_BODY = "Hello world!";

    private static final String INDEX_PAGE_PATH = "/index.html";
    private static final String LOGIN_PAGE_PATH = "/login.html";
    private static final String UNAUTHORIZED_PAGE_PATH = "/401.html";

    private static final String LOGIN_ACCOUNT_KEY = "account";
    private static final String LOGIN_PASSWORD_KEY = "password";
    private static final String REGISTER_ACCOUNT_KEY = "account";
    private static final String REGISTER_PASSWORD_KEY = "password";
    private static final String REGISTER_EMAIL_KEY = "email";

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
             final var inputReader = new BufferedReader(new InputStreamReader(inputStream));
             final var outputStream = connection.getOutputStream()) {

            final var requestLine = new RequestLine(inputReader.readLine());
            final var requestHeader = parseRequestHeader(inputReader);
            final var requestBody = parseRequestBody(inputReader, requestHeader);
            final var httpRequest = new HttpRequest(requestLine, requestHeader, requestBody);

            final var response = createResponse(httpRequest);

            outputStream.write(response.buildResponse().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private HttpRequestHeaders parseRequestHeader(final BufferedReader inputReader) throws IOException {
        final var requestHeaders = new ArrayList<String>();
        while (inputReader.ready()) {
            final var line = inputReader.readLine();
            if (line.isEmpty()) {
                break;
            }
            requestHeaders.add(line);
        }
        return new HttpRequestHeaders(requestHeaders);
    }

    private HttpRequestBody parseRequestBody(final BufferedReader inputReader, final HttpRequestHeaders requestHeaders) throws IOException {
        if (!requestHeaders.containsContentLength()) {
            return new HttpRequestBody();
        }
        int contentLength = requestHeaders.getContentLength();
        char[] buffer = new char[contentLength];
        inputReader.read(buffer, 0, contentLength);
        return new HttpRequestBody(new String(buffer));
    }

    private HttpResponse createResponse(final HttpRequest httpRequest) {
        final var path = httpRequest.getHttpPath();
        if (path.equals(LOGIN_PATH)) {
            return handleLoginPath(httpRequest);
        }
        if (path.equals(REGISTER_PATH)) {
            return handleRegisterPath(httpRequest);
        }
        if (path.equals(ROOT_PATH)) {
            return handleRootPath(httpRequest);
        }
        return handlePath(httpRequest);
    }

    private HttpResponse handleLoginPath(final HttpRequest httpRequest) {
        if (httpRequest.isGetMethod()) {
            return handlePath(httpRequest);
        }
        if (httpRequest.isPostMethod()) {
            return handleLoginPostRequest(httpRequest);
        }
        throw new RuntimeException();
    }

    private static HttpResponse handleLoginPostRequest(HttpRequest httpRequest) {
        final var requestBody = httpRequest.getHttpRequestBody();
        final var account = requestBody.get(LOGIN_ACCOUNT_KEY);
        final var password = requestBody.get(LOGIN_PASSWORD_KEY);
        final var user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 account입니다."));
        if (user.checkPassword(password)) {
            return HttpResponse.found(httpRequest.getHttpProtocol(), INDEX_PAGE_PATH);
        }
        return HttpResponse.found(httpRequest.getHttpProtocol(), UNAUTHORIZED_PAGE_PATH);
    }

    private HttpResponse handleRegisterPath(final HttpRequest httpRequest) {
        if (httpRequest.isGetMethod()) {
            return handlePath(httpRequest);
        }
        if (httpRequest.isPostMethod()) {
            return handleRegisterPostRequest(httpRequest);
        }
        throw new RuntimeException();
    }

    private static HttpResponse handleRegisterPostRequest(HttpRequest httpRequest) {
        final var requestBody = httpRequest.getHttpRequestBody();
        final var saveRequestUer = new User(
                requestBody.get(REGISTER_ACCOUNT_KEY),
                requestBody.get(REGISTER_PASSWORD_KEY),
                requestBody.get(REGISTER_EMAIL_KEY)
        );
        InMemoryUserRepository.save(saveRequestUer);
        return HttpResponse.found(httpRequest.getHttpProtocol(), LOGIN_PAGE_PATH);
    }

    private HttpResponse handleRootPath(final HttpRequest httpRequest) {
        return HttpResponse.ok(
                httpRequest.getHttpProtocol(),
                ContentType.TEXT_HTML,
                ROOT_BODY
        );
    }

    private HttpResponse handlePath(final HttpRequest httpRequest) {
        return HttpResponse.ok(
                httpRequest.getHttpProtocol(),
                parseContentType(httpRequest),
                parseResponseBody(httpRequest)
        );
    }

    private String parseResponseBody(final HttpRequest httpRequest) {
        return FileUtil.readStaticPathFileResource(httpRequest.getFilePath(), getClass());
    }

    private ContentType parseContentType(final HttpRequest httpRequest) {
        String extension = FileUtil.parseExtension(httpRequest.getFilePath());
        return ContentType.fromExtension(extension);
    }
}
