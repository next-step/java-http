package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Resource;
import camp.nextstep.http.domain.Response;
import camp.nextstep.http.exception.InvalidHttpRequestSpecException;
import camp.nextstep.http.handler.RequestHandlerContainer;

import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private final Socket connection;
    private final RequestHandlerContainer requestHandlerContainer;

    public Http11Processor(
        final Socket connection
    ) {
        this.connection = connection;
        this.requestHandlerContainer = new RequestHandlerContainer();
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
            // List<String> requestStrs = bufferedReader.lines().toList();
            List<String> requestStrs = List.of(bufferedReader.readLine());
            Response response = requestHandlerContainer.handleRequest(requestStrs);
            writeResponse(response.getResponseStr(), outputStream);
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void writeResponse(String string, OutputStream outputStream) throws IOException {
        outputStream.write(string.getBytes());
        outputStream.flush();
    }
}
