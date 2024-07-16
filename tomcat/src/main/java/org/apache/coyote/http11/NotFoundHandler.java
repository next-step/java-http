package org.apache.coyote.http11;


public class NotFoundHandler implements RequestHandler {

    public static final RequestHandler INSTANCE = new NotFoundHandler();

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        final var requestLine = httpRequest.getRequestLine();
        return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.NOT_FOUND, new HttpResponseHeaders(MimeType.TEXT_HTML));
    }

}