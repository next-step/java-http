package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.request.AbstractRequestHandler;

import java.io.IOException;

public class DefaultHandler extends AbstractRequestHandler {

    @Override
    public String handle(final HttpRequest request) throws IOException {
        final HttpHeaders httpHeaders = request.httpRequestHeader();
        final String body = buildBodyFromReadFile(httpHeaders.requestLine().url());
        return buildOkHttpResponse(httpHeaders, body);
    }
}
