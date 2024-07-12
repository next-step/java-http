package org.apache.coyote.http11;

import java.nio.charset.StandardCharsets;

public class ApplicationRequestHandler implements RequestHandler {

    @Override
    public Response service(RequestLine requestLine) {
        final var responseBody = "Hello world!";
        return new Response(requestLine.getHttpProtocol(), HttpStatusCode.OK, ContentType.TEXT_HTML, StandardCharsets.UTF_8, responseBody.getBytes());
    }
}
