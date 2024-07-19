package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.*;

import java.io.IOException;

public class StaticResourceRequestHandler implements RequestHandler {

    public static final StaticResourceRequestHandler INSTANCE = new StaticResourceRequestHandler();
    private static final String STATIC_RESOURCE_PATH = "static";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        final var requestLine = httpRequest.getRequestLine();
        try {
            byte[] readFile = FileLoader.read(STATIC_RESOURCE_PATH + requestLine.getPath());
            HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders(MimeType.from(requestLine.getPath()));

            if (requestLine.pathEndsWith("500.html")) {
                StatusLine statusLine = new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR);
                return new HttpResponse(statusLine, httpResponseHeaders, new MessageBody(readFile));
            }
            StatusLine statusLine = new StatusLine(requestLine.getHttpProtocol(), HttpStatusCode.OK);
            return new HttpResponse(statusLine, httpResponseHeaders, new MessageBody(readFile));
        } catch (IOException e) {
            throw new UncheckedServletException(e);
        }
    }

}
