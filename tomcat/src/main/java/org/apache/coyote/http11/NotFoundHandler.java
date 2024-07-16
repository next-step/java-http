package org.apache.coyote.http11;


import java.nio.charset.StandardCharsets;

public class NotFoundHandler implements RequestHandler {

    public static final RequestHandler INSTANCE = new NotFoundHandler();

    @Override
    public Response service(RequestLine requestLine) {
        return new Response(requestLine.getHttpProtocol(), HttpStatusCode.NOT_FOUND, ContentType.TEXT_HTML, StandardCharsets.UTF_8);
    }


}