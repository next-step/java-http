package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import camp.nextstep.request.Request;
import camp.nextstep.request.RequestParser;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final RequestParser requestParser;
    private final StaticResourceLoader staticResourceLoader;

    public Http11Processor(final Socket connection) {
        this.connection = connection;

        this.requestParser = new RequestParser();
        this.staticResourceLoader = new StaticResourceLoader();
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

            Request request = requestParser.parse(bufferedReader);

            processForLogin(request);

            String responseBody = getResponseBody(request.getPath());

            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: " + request.getPredictedMimeType() + ";charset=utf-8 ",
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processForLogin(Request request) {
        Object account = request.getQueryParameter("account");
        Object password = request.getQueryParameter("password");

        if (Objects.nonNull(account) && Objects.nonNull(password)) {
            Optional<User> user = findUser(account.toString(), password.toString());
            if (user.isPresent()) {
                System.out.println("user: " + user);
            }
        }
    }

    private Optional<User> findUser(String account, String password) {
        return InMemoryUserRepository.findByAccount(account)
                .filter(it -> it.checkPassword(password));
    }

    private String getResponseBody(String path) throws IOException, URISyntaxException {
        if (path.isEmpty() || path.equals("/")) {
            return "Hello world!";
        }

        if (path.equals("/index.html")) {
            return staticResourceLoader.readAllLines("static/index.html");
        }

        if (path.startsWith("/assets/") ||
                path.startsWith("/js/") ||
                path.startsWith("/css/")) {
            return staticResourceLoader.readAllLines("static" + path);
        }

        if (path.equals("/login")) {
            return staticResourceLoader.readAllLines("static/login.html");
        }

        // TODO: 404 NOT FOUND exception
        throw new IllegalArgumentException("잘못된 path 입니다: " + path);
    }
}
