package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.domain.http.ContentType;
import camp.nextstep.domain.http.HttpResponse;
import camp.nextstep.domain.http.RequestLine;
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

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_ACCOUNT_KEY = "account";
    private static final String LOGIN_PASSWORD_KEY = "password";
    private static final String ROOT_BODY = "Hello world!";

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
            final var response = createResponse(requestLine);

            outputStream.write(response.buildResponse().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private HttpResponse createResponse(final RequestLine requestLine) {
        final var path = requestLine.getHttpPath();
        if (path.equals(LOGIN_PATH)) {
            return handleLoginPath(requestLine);
        }
        if (path.equals(REGISTER_PATH)) {
            return handleRegisterPath(requestLine);
        }
        if (path.equals(ROOT_PATH)) {
            return handleRootPath(requestLine);
        }
        return handlePath(requestLine);
    }

    private HttpResponse handleLoginPath(final RequestLine requestLine) {
        final var queryString = requestLine.getQueryString();
        if (queryString.isEmpty()) {
            return HttpResponse.ok(
                    requestLine.getHttpProtocol(),
                    parseContentType(requestLine),
                    parseResponseBody(requestLine)
            );
        }
        final var account = queryString.get(LOGIN_ACCOUNT_KEY);
        final var password = queryString.get(LOGIN_PASSWORD_KEY);
        User user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 account입니다."));
        if (user.checkPassword(password)) {
            return HttpResponse.found(requestLine.getHttpProtocol(), "/index.html");
        }
        return HttpResponse.found(requestLine.getHttpProtocol(), "/401.html");
    }

    private HttpResponse handleRegisterPath(final RequestLine requestLine) {
        if (requestLine.isGetMethod()) {
            return HttpResponse.ok(
                    requestLine.getHttpProtocol(),
                    parseContentType(requestLine),
                    parseResponseBody(requestLine)
            );
        }
        throw new RuntimeException();
    }

    private HttpResponse handleRootPath(final RequestLine requestLine) {
        return HttpResponse.ok(
                requestLine.getHttpProtocol(),
                ContentType.TEXT_HTML,
                ROOT_BODY
        );
    }

    private HttpResponse handlePath(final RequestLine requestLine) {
        return HttpResponse.ok(
                requestLine.getHttpProtocol(),
                parseContentType(requestLine),
                parseResponseBody(requestLine)
        );
    }

    private String parseResponseBody(final RequestLine requestLine) {
        return FileUtil.readStaticPathFileResource(requestLine.getFilePath(), getClass());
    }

    private ContentType parseContentType(final RequestLine requestLine) {
        String extension = FileUtil.parseExtension(requestLine.getFilePath());
        return ContentType.fromExtension(extension);
    }
}
