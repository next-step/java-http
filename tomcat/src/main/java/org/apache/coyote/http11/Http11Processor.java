package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.config.ServerStartUpConfig;
import camp.nextstep.http.domain.response.HttpResponse;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private final Socket connection;
    private final ServerStartUpConfig serverStartUpConfig = new ServerStartUpConfig();
    public Http11Processor(
        final Socket connection
    ) {
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
             final var outputStream = connection.getOutputStream()
        ) {
             HttpResponse httpResponse = serverStartUpConfig.getHttpRequestHandlerContainer()
                     .handleRequest(inputStream);
             writeResponse(httpResponse.getResponseStr(), outputStream);
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void writeResponse(String string, OutputStream outputStream) throws IOException {
        outputStream.write(string.getBytes());
        outputStream.flush();
    }
}
