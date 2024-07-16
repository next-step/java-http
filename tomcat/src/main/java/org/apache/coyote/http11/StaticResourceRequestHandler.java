package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;

import java.io.IOException;

public class StaticResourceRequestHandler implements RequestHandler {

    public static final StaticResourceRequestHandler INSTANCE = new StaticResourceRequestHandler();
    private static final String STATIC_RESOURCE_PATH = "static";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        final var requestLine = httpRequest.getRequestLine();
        try {
            byte[] readFile = FileLoader.read(STATIC_RESOURCE_PATH + requestLine.getPath());
            HttpHeaders httpHeaders = new HttpHeaders(MimeType.from(requestLine.getPath()));

            if (requestLine.pathEndsWith("500.html")) {
                return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.INTERNAL_SERVER_ERROR, httpHeaders, new ResponseBody(readFile));
            }
            return new HttpResponse(requestLine.getHttpProtocol(), HttpStatusCode.OK, httpHeaders, new ResponseBody(readFile));
        } catch (IOException e) {
            throw new UncheckedServletException(e);
        }
    }

}
