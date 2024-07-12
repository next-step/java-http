package org.apache.coyote.http11;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.model.User;
import org.apache.coyote.Processor;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.request.RequestBody;
import org.apache.coyote.request.RequestHeaders;
import org.apache.coyote.request.RequestLine;
import org.apache.coyote.response.FileFinder;
import org.apache.coyote.response.HttpResponse;
import org.apache.coyote.response.HttpStatus;
import org.apache.coyote.response.MimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final String ACCOUNT_KEY = "account";
    private static final String ROOT_CONTENT = "Hello world!";

    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/register";

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
            HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, requestBody);

            HttpResponse response = getResponse(httpRequest);

            outputStream.write(response.buildContent().getBytes());
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

    private HttpResponse getResponse(HttpRequest request) throws IOException {
        String httpPath = request.getHttpPath();
        if (httpPath.endsWith(LOGIN_PATH)) {
            return handleLogin(request);
        }
        if (httpPath.endsWith(REGISTER_PATH)) {
            return handleRegister(request);
        }
        if (httpPath.equals(ROOT_PATH)) {
            return handleRoot(request);
        }
        return handleDefault(request);
    }

    private HttpResponse handleLogin(HttpRequest request) throws IOException {
        if (request.isGet()) {
            MimeType mimeType = MimeType.HTML;
            return new HttpResponse(
                    HttpStatus.OK,
                    mimeType,
                    FileFinder.find(LOGIN_PATH + mimeType.getFileExtension()));
        }
        if (request.isPost()) {
            RequestBody requestBody = request.getRequestBody();
            Optional<User> optionalUser = InMemoryUserRepository.findByAccount(requestBody.get(ACCOUNT_KEY));
            if (optionalUser.isPresent() && optionalUser.get().checkPassword(requestBody.get("password"))) {
                log.info(optionalUser.get().toString());
                return new HttpResponse(
                        HttpStatus.FOUND,
                        MimeType.HTML,
                        FileFinder.find("/index.html"));
            }
            return new HttpResponse(
                    HttpStatus.UNAUTHORIZED,
                    MimeType.HTML,
                    FileFinder.find("/401.html"));
        }
        return new HttpResponse(
                HttpStatus.NOT_FOUND,
                MimeType.HTML,
                FileFinder.find("/404.html"));
    }

    private HttpResponse handleRegister(HttpRequest request) throws IOException {
        if (request.isGet()) {
            return new HttpResponse(
                    HttpStatus.OK,
                    MimeType.HTML,
                    FileFinder.find("/register.html"));
        }
        if (request.isPost()) {
            RequestBody requestBody = request.getRequestBody();
            User user = new User(requestBody.get("account"), requestBody.get("password"), requestBody.get("email"));
            InMemoryUserRepository.save(user);

            return new HttpResponse(
                    HttpStatus.CREATED,
                    MimeType.HTML,
                    FileFinder.find("/index.html"));
        }
        return null;
    }

    private HttpResponse handleRoot(HttpRequest request) throws IOException {
        if (request.isGet()) {
            return new HttpResponse(
                    HttpStatus.OK,
                    MimeType.HTML,
                    ROOT_CONTENT);
        }
        return new HttpResponse(
                HttpStatus.NOT_FOUND,
                MimeType.HTML,
                FileFinder.find("/404.html"));
    }

    private HttpResponse handleDefault(HttpRequest request) throws IOException {
        String httpPath = request.getHttpPath();
        return new HttpResponse(
                HttpStatus.OK,
                parseMimeType(httpPath),
                FileFinder.find(httpPath));
    }

    private MimeType parseMimeType(String httpPath) {
        return MimeType.from(httpPath);
    }
}
