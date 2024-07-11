package org.apache.coyote.http11;

import camp.nextstep.controller.RequestMapping;
import camp.nextstep.domain.http.HttpCookie;
import camp.nextstep.domain.http.HttpHeaders;
import camp.nextstep.domain.http.request.HttpRequest;
import camp.nextstep.domain.http.request.HttpRequestBody;
import camp.nextstep.domain.http.request.RequestLine;
import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final RequestMapping requestMapping = RequestMapping.create();

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

            final var requestHeader = HttpHeaders.from(parseRequestHeader(inputReader));
            final var requestCookie = HttpCookie.from(requestHeader);
            final var requestBody = parseRequestBody(inputReader, requestHeader);
            final var httpRequest = new HttpRequest(requestLine, requestHeader, requestCookie, requestBody);

            final var response = requestMapping.service(httpRequest);

            outputStream.write(response.buildResponse().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private List<String> parseRequestHeader(final BufferedReader inputReader) throws IOException {
        final var requestHeaders = new ArrayList<String>();
        while (inputReader.ready()) {
            final var line = inputReader.readLine();
            if (line.isEmpty()) {
                break;
            }
            requestHeaders.add(line);
        }
        return requestHeaders;
    }

    private HttpRequestBody parseRequestBody(final BufferedReader inputReader, final HttpHeaders requestHeaders) throws IOException {
        if (!requestHeaders.containsContentLength()) {
            return new HttpRequestBody();
        }
        int contentLength = requestHeaders.getContentLength();
        char[] buffer = new char[contentLength];
        inputReader.read(buffer, 0, contentLength);
        return HttpRequestBody.from(new String(buffer));
    }
}
