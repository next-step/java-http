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

            // 요청을 파싱한다
            String httpRequestMessage = readHttpRequestMessage(br);
            HttpRequest httpRequest = HttpRequest.from(httpRequestMessage);

            // body를 읽는다
            if (httpRequest.isPost()) {
                String headerValue = httpRequest.getHeaderValue(HttpHeaderName.CONTENT_LENGTH.getValue());
                long contentLength;
                if (headerValue == null || headerValue.isBlank()) {
                    contentLength = 0;
                } else {
                    contentLength = Long.parseLong(headerValue);
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < contentLength; i++) {
                    int read = br.read();
                    sb.append((char) read);
                }
                String body = sb.toString();
                httpRequest.addRequestBody(body);
            }

            // 요청을 처리한다
            HttpResponse response = handleRequest(httpRequest);

            // 응답을 출력한다
            outputStream.write(response.createFormat().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private HttpResponse handleRequest(final HttpRequest httpRequest) throws IOException {
        String path = httpRequest.getPath();
        if (path.contains("/login") && httpRequest.isPost()) {
            User user = InMemoryUserRepository.findByAccount(httpRequest.getBodyValue("account"))
                    .orElseThrow();
            String password = httpRequest.getBodyValue("password");

            String location;
            if (user.checkPassword(password)) {
                location = "/index.html";
            } else {
                location = "/401.html";
            }

            List<HttpHeader> httpHeaders = List.of(HttpHeader.of(HttpHeaderName.LOCATION.getValue(), location));
            return HttpResponse.of(httpRequest.getProtocol(), HttpStatus.FOUND, httpHeaders, "");
        }

        if (httpRequest.isGet()) {
            File resource = new ResourceFinder().findByPath(path);
            MediaType mediaType = MediaType.from(resource);
            List<HttpHeader> httpHeaders = List.of(HttpHeader.of(HttpHeaderName.CONTENT_TYPE.getValue(), mediaType.getValue() + ";charset=utf-8"));
            String responseBody = new String(Files.readAllBytes(resource.toPath()));
            return HttpResponse.of(httpRequest.getProtocol(), HttpStatus.OK, httpHeaders, responseBody);
        }

        List<HttpHeader> httpHeaders = List.of(HttpHeader.of(HttpHeaderName.LOCATION.getValue(), "/404.html"));
        return HttpResponse.of((httpRequest.getProtocol()), HttpStatus.FOUND, httpHeaders, "");
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
}
