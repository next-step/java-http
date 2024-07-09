package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.HttpPath;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;
import org.apache.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String ROOT_PATH_BODY = "Hello world!";
    private static final String LOGIN_PATH = "/login";
    private static final String ACCOUNT_PARAMETER = "account";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String ROOT_PATH = "/";
    private static final String INDEX_PATH = "/index.html";
    private static final String NOT_FOUND_PATH = "/404.html";
    private static final String UNAUTHORIZED_PATH = "/401.html";

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

            Response response = handleMapping(requestLine);

            outputStream.write(response.toHttp11());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Response handleMapping(RequestLine requestLine) throws IOException {
        HttpPath path = requestLine.getPath();
        if (isRootPath(path.getPath())) {
            return Response.ok(ContentType.HTML, ROOT_PATH_BODY);
        }

        if (isLoginPath(path)) {
            return handleLogin(requestLine);
        }

        String responseBody = getResponseBody(path);
        if (responseBody.isEmpty()) {
            return Response.notFound(getResponseBody(HttpPath.from(NOT_FOUND_PATH)));
        }
        return Response.ok(ContentType.from(path.getExtension()), getResponseBody(requestLine.getPath()));
    }

    private boolean isRootPath(String path) {
        return path.equals(ROOT_PATH);
    }

    private boolean isLoginPath(HttpPath path) {
        return LOGIN_PATH.equals(path.getPath());
    }

    private Response handleLogin(RequestLine requestLine) throws IOException {
        Map<String, Object> parameters = requestLine.getParameters();

        if (parameters.isEmpty()) {
            return Response.ok(ContentType.HTML, getResponseBody(requestLine.getPath()));
        }

        String account = parameters.get(ACCOUNT_PARAMETER).toString();
        String password = parameters.get(PASSWORD_PARAMETER).toString();

        return authenticateUser(account, password);
    }

    private Response authenticateUser(String account, String password) throws IOException {
        try {
            User user = findUserByAccount(account);

            if (!isPasswordValid(user, password)) {
                throw new IllegalArgumentException();
            }
            log.info(user.toString());
            return Response.redirect(getResponseBody(HttpPath.from(INDEX_PATH)));
        } catch (IllegalArgumentException e) {
            return Response.unauthorized(getResponseBody(HttpPath.from(UNAUTHORIZED_PATH)));
        }
    }

    private User findUserByAccount(String account) {
        return InMemoryUserRepository.findByAccount(account)
            .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isPasswordValid(User user, String password) {
        return user.checkPassword(password);
    }

    private String getResponseBody(HttpPath path) throws IOException {
        String resourcePath = ClassLoader.getSystemResource(STATIC_PATH).getPath();
        return FileUtils.readFileContent(resourcePath + path.getFilePath());
    }
}
