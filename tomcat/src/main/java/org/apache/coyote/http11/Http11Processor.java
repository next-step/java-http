package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.catalina.Session;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringJoiner;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private static final String CRLF = "\r\n";

    private final Socket connection;
    private final RequestHandler requestHandler;
    private final Session session;

    public Http11Processor(final Socket connection, final RequestHandler requestHandler, final Session session) {
        this.connection = connection;
        this.requestHandler = requestHandler;
        this.session = session;
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
            httpRequest.setSession(session);
            HttpResponse httpResponse = HttpResponse.from(httpRequest.getProtocol());
            requestHandler.handle(httpRequest, httpResponse);

            outputStream.write(httpResponse.createFormat().getBytes());
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
}
