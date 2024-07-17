package org.apache.coyote.http11;

import camp.nextstep.controller.Controller;
import camp.nextstep.controller.RequestMapping;
import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.apache.coyote.request.HttpCookie;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.request.RequestBody;
import org.apache.coyote.request.RequestHeaders;
import org.apache.coyote.request.RequestLine;
import org.apache.coyote.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
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
            HttpCookie httpCookie = HttpCookie.from(requestHeaders);
            RequestBody requestBody = getRequestBody(bufferedReader, requestHeaders);
            HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, httpCookie, requestBody);

            Controller controller = RequestMapping.get(httpRequest);
            HttpResponse response = controller.service(httpRequest);

//            HttpResponse response = getResponse(httpRequest);

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

//    private HttpResponse getResponse(HttpRequest request) {
//        String httpPath = request.getHttpPath();
//        return switch (httpPath) {
////            case LOGIN_PATH -> handleLogin(request);
////            case REGISTER_PATH -> handleRegister(request);
////            case ROOT_PATH -> handleRoot(request);
////            default -> handlePath(request);
//        };
//    }

//    private HttpResponse handleRegister(HttpRequest request) {
//        if (request.isGet()) {
//            return new HttpResponse(
//                    HttpStatus.OK,
//                    MimeType.HTML,
//                    FileFinder.find("/register.html"));
//        }
//        if (request.isPost()) {
//            RequestBody requestBody = request.getRequestBody();
//            User user = new User(requestBody.get("account"), requestBody.get("password"), requestBody.get("email"));
//            InMemoryUserRepository.save(user);
//
//            return new HttpResponse(
//                    HttpStatus.CREATED,
//                    MimeType.HTML,
//                    FileFinder.find("/index.html"));
//        }
//        return null;
//    }

//    private HttpResponse handleRoot(HttpRequest request) {
//        if (request.isGet()) {
//            return new HttpResponse(
//                    HttpStatus.OK,
//                    MimeType.HTML,
//                    ROOT_CONTENT);
//        }
//        return HttpResponse.notFound();
//    }

//    private HttpResponse handlePath(HttpRequest request) {
//        String httpPath = request.getHttpPath();
//        return new HttpResponse(
//                HttpStatus.OK,
//                MimeType.from(httpPath),
//                FileFinder.find(httpPath));
//    }
}
