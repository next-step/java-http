package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.request.RequestHandlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class Http11Processor implements Runnable, Processor {

    private static final String EMPTY_STRING = "";

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

            final List<String> requestHeaderLines = bufferedReader.lines()
                    .takeWhile(line -> !line.isEmpty())
                    .toList();

            final HttpHeaders httpHeaders = HttpRequestHeaderParser.getInstance()
                    .parse(requestHeaderLines);

            final String requestBodyLine = readRequestBodyLine(bufferedReader, httpHeaders);

            final HttpRequest httpRequest = HttpRequestParser.getInstance()
                    .parse(httpHeaders, requestBodyLine);

            final String response = RequestHandlerMapper.getInstance()
                    .response(httpRequest);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String readRequestBodyLine(final BufferedReader bs, final HttpHeaders httpHeaders) throws IOException {
        if (httpHeaders.hasRequestBody()) {
            final int contentLength = httpHeaders.contentLength();

            final char[] body = new char[contentLength];
            bs.read(body);

            return new String(body);
        }

        return EMPTY_STRING;
    }
}
