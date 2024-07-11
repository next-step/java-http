package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import org.apache.coyote.Processor;
import org.apache.coyote.request.RequestBody;
import org.apache.coyote.request.RequestHeaders;
import org.apache.coyote.request.RequestLine;
import org.apache.coyote.response.MimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String ROOT_PATH = "/";
    private static final String ROOT_CONTENT = "Hello world!";
    public static final String LOGIN_PATH = "/login";
    public static final String ACCOUNT_KEY = "account";

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
             final var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
             final var outputStream = connection.getOutputStream()) {

            RequestLine requestLine = getRequestLine(bufferedReader);
            RequestHeaders requestHeaders = getRequestHeaders(bufferedReader);
            RequestBody requestBody = getRequestBody(bufferedReader, requestHeaders);

            String response = getResponse(requestLine, requestBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static RequestLine getRequestLine(BufferedReader bufferedReader) throws IOException {
        String requestLineValue = bufferedReader.readLine();
        return RequestLine.parse(requestLineValue);
    }

    private static RequestHeaders getRequestHeaders(BufferedReader bufferedReader) {
        List<String> requestHeaderValues = bufferedReader.lines()
                .takeWhile(line -> !line.isEmpty())
                .toList();
        return RequestHeaders.parse(requestHeaderValues);
    }

    private RequestBody getRequestBody(BufferedReader bufferedReader, RequestHeaders requestHeaders) throws IOException {
        if (requestHeaders.getHeader("Content-Length") == null) {
            return new RequestBody();
        }
        int contentLength = Integer.parseInt(requestHeaders.getHeader("Content-Length"));
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        return RequestBody.parse(new String(buffer));
    }

    private String getResponse(RequestLine requestLine, RequestBody requestBody) throws IOException {
        String httpPath = requestLine.getHttpPath();
        String responseBody = findResponseBody(httpPath);
        MimeType mimeType = MimeType.from(httpPath);

        String httpStatus = "200 OK";

        if (httpPath.endsWith(LOGIN_PATH) && requestLine.getHttpMethod().isPost()) {
            Optional<User> optionalUser = InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT_KEY));
            if (optionalUser.isPresent() && optionalUser.get().checkPassword(requestBody.get("password"))) {
                httpStatus = "302 Found";
                responseBody = findResponseBody("/index.html");
                log.info(optionalUser.get().toString());
            } else {
                httpStatus = "401 Unauthorized";
                responseBody = findResponseBody("/401.html");
            }
            log.info("requestBody: {}", requestBody);
        }

        if (httpPath.endsWith("/register")) {
            if (requestLine.getHttpMethod().isGet()) {
                responseBody = findResponseBody("/register.html");
            }
            if (requestLine.getHttpMethod().isPost()) {
                User user = new User(requestBody.get("account"), requestBody.get("password"), requestBody.get("email"));
                InMemoryUserRepository.save(user);
                httpStatus = "201 Created";
                responseBody = findResponseBody("/index.html");
            }
        }

        return String.join("\r\n",
                "HTTP/1.1 " + httpStatus + " ",
                "Content-Type: " + mimeType.getContentType() + " ",
                "Content-Length: " + responseBody.getBytes().length + " ",
                "",
                responseBody);
    }

    private String findResponseBody(String httpPath) throws IOException {
        if (httpPath.equals(ROOT_PATH)) {
            return ROOT_CONTENT;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        if (httpPath.endsWith(LOGIN_PATH)) {
            httpPath += MimeType.HTML.getFileExtension();
        }
        if (httpPath.endsWith("/register")) {
            httpPath += MimeType.HTML.getFileExtension();
        }
        URL resource = classLoader.getResource("static" + httpPath);
        Path path = Path.of(Objects.requireNonNull(resource).getPath());
        return new String(Files.readAllBytes(path));
    }
}
