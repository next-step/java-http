package org.apache.coyote;

import java.util.Map;

public class DefaultHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        final var responseBody = "Hello world!";
        final var statusLine = new HttpResponseStatusLine(Protocol.HTTP.name(), Protocol.HTTP.version, HttpStatus.OK.statusCode, HttpStatus.OK.name());
        final var responseHeaders = Map.of(HttpHeaders.CONTENT_LENGTH, Integer.toString(responseBody.getBytes().length));
        final var representationHeaders = Map.of(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8");

        return new HttpResponse(statusLine, responseHeaders, representationHeaders, responseBody);
    }

}
