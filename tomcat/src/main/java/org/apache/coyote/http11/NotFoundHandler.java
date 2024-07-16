package org.apache.coyote.http11;


import java.nio.charset.StandardCharsets;

public class NotFoundHandler implements RequestHandler {

    public static final RequestHandler INSTANCE = new NotFoundHandler();

    @Override
    public HttpResponse service(RequestLine requestLine) {
        return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.NOT_FOUND, ContentType.TEXT_HTML);
    }


}