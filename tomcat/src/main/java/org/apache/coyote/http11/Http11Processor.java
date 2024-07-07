package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.model.HttpRequestHeader;
import org.apache.coyote.http11.model.QueryParams;
import org.apache.coyote.http11.model.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String STATIC_PATH = "static";
    private static final String ROOT_PATH_RESPONSE_BODY = "Hello world!";
    private static final String LOGIN_PATH = "/login.html";
    private static final String LOGIN_ACCOUNT_KEY = "account";
    private static final String LOGIN_PASSWORD_KEY = "password";
    private static final HttpRequestHeaderParser HTTP_REQUEST_HEADER_PARSER = new HttpRequestHeaderParser();

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
             final var outputStream = connection.getOutputStream();
             final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            final List<String> requestLines = bufferedReader.lines()
                    .takeWhile(line -> !line.isEmpty())
                    .toList();

            final HttpRequestHeader httpRequestHeader = HTTP_REQUEST_HEADER_PARSER.parse(requestLines);
            final RequestLine requestLine = httpRequestHeader.requestLine();

            if (isLogin(requestLine)) {
                loggingUser(requestLine.queryParams());
            }

            final var responseBody = readHtmlResponseBody(httpRequestHeader.requestLine());
            final var response = String.join("\r\n",
                    "HTTP/1.1 200 OK ",
                    "Content-Type: " + httpRequestHeader.requestLine().contentTypeText() + ";charset=utf-8 ",
                    "Content-Length: " + responseBody.getBytes().length + " ",
                    "",
                    responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean isLogin(final RequestLine requestLine) {
        return requestLine.url()
                .equals(LOGIN_PATH);
    }

    private void loggingUser(final QueryParams queryParams) {
        final User user = InMemoryUserRepository.findByAccount(queryParams.valueBy(LOGIN_ACCOUNT_KEY))
                .orElseThrow(NoSuchElementException::new);
        
        if (user.checkPassword(queryParams.valueBy(LOGIN_PASSWORD_KEY))) {
            log.info("user {}", user);
        }
    }

    private String readHtmlResponseBody(final RequestLine requestLine) throws IOException {
        if (requestLine.isRootPath()) {
            return ROOT_PATH_RESPONSE_BODY;
        }
        final URL path = getClass().getClassLoader().getResource(STATIC_PATH + requestLine.url());
        final File html = new File(path.getFile());

        return new String(Files.readAllBytes(html.toPath()));
    }
}
