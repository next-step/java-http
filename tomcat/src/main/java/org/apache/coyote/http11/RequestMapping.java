package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RequestMapping {

    public RequestHandler getHandler(RequestLine requestLine) {

        if (FileLoader.isStaticResource(requestLine.getPath())) {
            return new RequestHandler() {
                @Override
                public Response service(RequestLine requestLine) {
                    try {
                        byte[] readFile = FileLoader.read("static" + requestLine.getPath());
                        if (requestLine.pathEndsWith("500.html")) {
                            return new Response(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, ContentType.from(requestLine.getPath()), StandardCharsets.UTF_8, readFile);
                        }
                        return new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.from(requestLine.getPath()), StandardCharsets.UTF_8, readFile);
                    } catch (IOException e) {
                        throw new UncheckedServletException(e);
                    }
                }
            };
        }

        return new RequestHandler() {
            @Override
            public Response service(RequestLine requestLine) {
                final var responseBody = "Hello world!";
                return new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, StandardCharsets.UTF_8, responseBody.getBytes());
            }
        };
    }
}
