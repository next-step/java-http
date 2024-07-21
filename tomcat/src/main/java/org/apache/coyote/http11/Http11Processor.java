package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.CoyoteAdapter;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.HttpRequestLineInvalidException;
import org.apache.coyote.http11.request.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final CoyoteAdapter adapter = new CoyoteAdapter();

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

            final var httpRequest = RequestParser.parse(inputStream);
            adapter.parseSessionCookieId(httpRequest);

            final var requestMapping = new RequestMapping();
            final var handler = requestMapping.getHandler(httpRequest.getRequestLine());

            final var response = handler.service(httpRequest);

            outputStream.write(response.generateMessage().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException | HttpRequestLineInvalidException e) {
            log.error(e.getMessage(), e);
        }
    }
}
