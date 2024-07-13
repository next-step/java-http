package org.apache.coyote.http11;

import camp.nextstep.controller.RequestMapping;
import camp.nextstep.exception.RequestNotFoundException;
import camp.nextstep.request.HttpRequest;
import camp.nextstep.request.HttpRequestParser;
import camp.nextstep.response.HttpResponse;
import camp.nextstep.response.ResponseStatusCode;
import camp.nextstep.staticresource.StaticResourceLoader;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;

    private final HttpRequestParser requestParser;
    private final RequestMapping requestMapping;
    private final StaticResourceLoader staticResourceLoader;

    public Http11Processor(final Socket connection) {
        this.connection = connection;

        this.requestParser = new HttpRequestParser();
        this.requestMapping = new RequestMapping();
        this.staticResourceLoader = new StaticResourceLoader();
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var inputStreamReader = new InputStreamReader(inputStream);
             final var bufferedReader = new BufferedReader(inputStreamReader);
             final var outputStream = connection.getOutputStream()
        ) {
            HttpRequest request = requestParser.parse(bufferedReader);
            HttpResponse response = new HttpResponse(outputStream, request, staticResourceLoader);
            response.setSessionCookie();

            try {
                requestMapping.getController(request).service(request, response);
            } catch (RequestNotFoundException e) {
                response.render(ResponseStatusCode.NotFound, "/404.html");
                throw e;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
