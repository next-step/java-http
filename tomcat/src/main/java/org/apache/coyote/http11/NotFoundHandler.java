package org.apache.coyote.http11;


import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.HttpResponseHeaders;
import org.apache.coyote.http11.response.HttpStatusCode;
import org.apache.coyote.http11.response.StatusLine;

public class NotFoundHandler implements RequestHandler {

    public static final RequestHandler INSTANCE = new NotFoundHandler();

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        final var requestLine = httpRequest.getRequestLine();
        ;
        return new HttpResponse(new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.NOT_FOUND), new HttpResponseHeaders(MimeType.TEXT_HTML));
    }

}