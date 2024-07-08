package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.catalina.connector.CoyoteAdapter;
import org.apache.coyote.Processor;
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

            final Http11Input http11Input = new Http11Input(inputStream);
            http11Input.parseRequestLine();

            final Request request = http11Input.getRequest();

            final Response response = new Response();
            response.init();

            adapter.service(request, response);

            outputStream.write(response.toBytes());
            outputStream.flush();
            http11Input.close();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
