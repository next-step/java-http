package org.apache.coyote.http11;


public class NotFoundHandler implements RequestHandler {

    public static final RequestHandler INSTANCE = new NotFoundHandler();

    @Override
    public HttpResponse service(RequestLine requestLine) {
        return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.NOT_FOUND, MimeType.TEXT_HTML);
    }


}