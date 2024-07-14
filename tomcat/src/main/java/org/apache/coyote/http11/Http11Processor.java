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

            final HttpResponse httpResponse = new HttpResponse();
            final HttpInputParser httpParser = new HttpInputParser(inputStream);

            processInternal(httpParser, httpResponse);

            outputStream.write(httpResponse.toBytes());
            httpParser.close();
            outputStream.flush();

        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processInternal(final HttpInputParser httpParser, final HttpResponse httpResponse) {
        try {
            httpParser.parseRequest();

            final HttpRequest httpRequest = httpParser.getRequest();
            httpResponse.init();

            adapter.service(httpRequest, httpResponse);
        } catch (HttpParseException e) {
            setErrorResponse(e.getMessage(), httpResponse, StatusCode.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            setErrorResponse(e.getMessage(), httpResponse, StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void setErrorResponse(final String e, final HttpResponse httpResponse, final StatusCode badRequest) {
        final String body = ErrorViewResolver.errorView(e);

        httpResponse.setBody(body, ContentType.TEXT_HTML);
        httpResponse.setResponseLine(HttpVersion.HTTP1_1, badRequest);
    }
}
