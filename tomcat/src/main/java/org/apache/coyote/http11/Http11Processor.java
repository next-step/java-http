package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.HttpResponse;
import camp.nextstep.http.handler.HttpRequestHandlerContainer;

import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private final Socket connection;
    private final HttpRequestHandlerContainer httpRequestHandlerContainer;

    public Http11Processor(
        final Socket connection
    ) {
        this.connection = connection;
        this.httpRequestHandlerContainer = new HttpRequestHandlerContainer();
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
//            StringBuilder stringBuilder = new StringBuilder();
//            writeInputStream(bufferedReader, stringBuilder);
//            List<String> requestStrs = Arrays.stream(stringBuilder.toString().split("\r\n")).toList();
//            HttpResponse httpResponse = httpRequestHandlerContainer.handleRequest(requestStrs);

             HttpResponse httpResponse = httpRequestHandlerContainer.handleRequest(inputStream);
             writeResponse(httpResponse.getResponseStr(), outputStream);
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void writeResponse(String string, OutputStream outputStream) throws IOException {
        outputStream.write(string.getBytes());
        outputStream.flush();
    }

    private void writeInputStream(
            final BufferedReader reader,
            final StringBuilder actual
    ) throws IOException {
        String s;
        boolean hasContent = false;
        while ((s = reader.readLine()) != null) {
            if (s.contains("Content-Length")) {
                hasContent = true;
            }
            if (s.isEmpty()) {
                break;
            }
            actual.append(s);
            actual.append("\r\n");
        }

        if (hasContent) {
            char[] buffer = new char[57];
            reader.read(buffer, 0, 57);
            actual.append(buffer);
        }
    }

//    private void writeInputStream(final Reader reader, final StringBuilder actual) throws IOException {
//        int c;
//        while ((c = reader.read()) != -1) {
//            actual.append((char) c);
//        }
//    }
}
