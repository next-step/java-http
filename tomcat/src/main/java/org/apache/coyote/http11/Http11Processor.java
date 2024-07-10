package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.catalina.connector.CoyoteAdapter;
import org.apache.coyote.Processor;
import org.apache.coyote.http.*;
import org.apache.coyote.view.ErrorViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;
    private final CoyoteAdapter adapter;

    public Http11Processor(final Socket connection, final CoyoteAdapter adapter) {
        this.connection = connection;
        this.adapter = adapter;
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

            final Response response = new Response();
            final HttpInputParser httpParser = new HttpInputParser(inputStream);

            processInternal(httpParser, response);

            outputStream.write(response.toBytes());
            httpParser.close();
            outputStream.flush();

        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processInternal(final HttpInputParser httpParser, final Response response) {
        try {
            httpParser.parseRequestLine();

            final Request request = httpParser.getRequest();
            response.init();

            adapter.service(request, response);
        } catch (HttpParseException e) {
            final String body = ErrorViewResolver.errorView(e.getMessage());

            response.setBody(body, ContentType.TEXT_HTML);
            response.setResponseLine(HttpVersion.HTTP1_1, StatusCode.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            final String body = ErrorViewResolver.errorView(e.getMessage());

            response.setBody(body, ContentType.TEXT_HTML);
            response.setResponseLine(HttpVersion.HTTP1_1, StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
}
