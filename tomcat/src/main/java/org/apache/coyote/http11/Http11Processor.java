package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.model.HttpRequestHeader;
import org.apache.coyote.http11.model.RequestLine;
import org.apache.coyote.http11.request.RequestHandlerMapper;
import org.apache.coyote.http11.request.handler.RequestHandler;
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
             final var outputStream = connection.getOutputStream();
             final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            final List<String> requestLines = bufferedReader.lines()
                    .takeWhile(line -> !line.isEmpty())
                    .toList();

            final HttpRequestHeader httpRequestHeader = HttpRequestHeaderParser.getInstance()
                    .parse(requestLines);
            final RequestLine requestLine = httpRequestHeader.requestLine();

            final RequestHandler handler = RequestHandlerMapper.getInstance()
                    .getHandler(requestLine.url());
            final String response = handler.handle(httpRequestHeader);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
