package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.model.RequestHeader;
import org.apache.coyote.http11.request.RequestHeaderParser;
import org.apache.coyote.http11.request.model.RequestLine;
import org.apache.coyote.http11.response.Response;
import org.apache.coyote.http11.response.ResponseResource;
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

            if (bufferedReader.lines() == null) {
                throw new IllegalArgumentException("요청값이 빈값입니다.");
            }
            List<String> requestHeaders = bufferedReader.lines().takeWhile(line -> !line.isEmpty()).toList();
            RequestHeader requestHeader = RequestHeaderParser.parse(bufferedReader, requestHeaders);
            RequestLine requestLine = requestHeader.getRequestLine();

            ResponseResource responseResource = ResponseResource.of(requestLine.getPath(),  requestHeader.getRequestBodies(), requestLine.getHttpMethod());

            Response response = Response.createResponse(responseResource);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
