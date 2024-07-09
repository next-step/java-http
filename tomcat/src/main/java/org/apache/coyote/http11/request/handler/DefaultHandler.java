package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpRequestHeader;

import java.io.IOException;

public class DefaultHandler extends AbstractRequestHandler {

    @Override
    public String handle(final HttpRequest request) throws IOException {
        final HttpRequestHeader httpRequestHeader = request.httpRequestHeader();
        final String body = buildBodyFromReadFile(httpRequestHeader.requestLine().url());
        return buildOkHttpResponse(httpRequestHeader, body);
    }
}
