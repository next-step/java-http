package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StaticResourceRequestHandler implements RequestHandler {

    private static final String STATIC_RESOURCE_PATH = "static";

    @Override
    public Response service(RequestLine requestLine) {
        try {
            byte[] readFile = FileLoader.read(STATIC_RESOURCE_PATH + requestLine.getPath());
            if (requestLine.pathEndsWith("500.html")) {
                return new Response(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, ContentType.from(requestLine.getPath()), StandardCharsets.UTF_8, readFile);
            }
            return new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.from(requestLine.getPath()), StandardCharsets.UTF_8, readFile);
        } catch (IOException e) {
            throw new UncheckedServletException(e);
        }
    }

}
