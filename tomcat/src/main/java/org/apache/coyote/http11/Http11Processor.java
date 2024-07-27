package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.apache.coyote.Processor;
import camp.nextstep.controller.ControllerFactory;
import camp.nextstep.controller.ControllerFactoryProvider;
import org.apache.coyote.http11.parser.HttpRequestDto;
import org.apache.coyote.http11.parser.HttpRequestParser;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.factory.Http11RequestFactoryProvider;
import org.apache.coyote.http11.request.factory.HttpRequestFactory;
import org.apache.coyote.http11.request.factory.HttpRequestFactoryProvider;
import org.apache.coyote.http11.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private static final HttpRequestFactoryProvider httpRequestFactoryProvider = new Http11RequestFactoryProvider();
    private static final ControllerFactoryProvider controllerFactoryProvider = new ControllerFactoryProvider();

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
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final var outputStream = connection.getOutputStream()) {
            HttpRequestDto httpRequestDto = HttpRequestParser.parse(bufferedReader);

            HttpRequestFactory httpRequestFactory = httpRequestFactoryProvider.provideFactory(
                httpRequestDto.requestMethod);
            HttpRequest httpRequest = httpRequestFactory.createHttpInstance(httpRequestDto);
            log.info(httpRequest.toString());

            ControllerFactory controllerFactory = controllerFactoryProvider.provideFactory(
                httpRequest.getRequestUrl());
            HttpResponse httpResponse = controllerFactory.serve(httpRequest);

            log.info(httpResponse.toString());
            String response = httpResponse.write();
            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

}
