package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import java.io.IOException;
import java.net.Socket;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.parser.HttpRequestDto;
import org.apache.coyote.http11.parser.HttpRequestParser;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.factory.Http11FactoryProvider;
import org.apache.coyote.http11.request.factory.HttpRequestFactoryProvider;
import org.apache.coyote.http11.request.factory.HttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final HttpRequestFactoryProvider httpFactoryProvider = new Http11FactoryProvider();
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
            final var outputStream = connection.getOutputStream()) {
            HttpRequestDto httpRequestDto = HttpRequestParser.of(inputStream);
            HttpRequestFactory httpRequestFactory = httpFactoryProvider.provideFactory(
                httpRequestDto.requestMethod);
            HttpRequest httpRequest = httpRequestFactory.createHttpInstance(httpRequestDto);

            final var responseBody = "Hello world!";

            final var response = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: " + responseBody.getBytes().length + " ",
                "",
                responseBody);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void parseRequestLine() {

    }
}
