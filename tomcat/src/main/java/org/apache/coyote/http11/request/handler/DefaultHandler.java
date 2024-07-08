package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.model.HttpRequestHeader;

import java.io.IOException;

public class DefaultHandler extends AbstractRequestHandler {

    @Override
    public String handle(final HttpRequestHeader requestHeader) throws IOException {
        final String body = buildBodyFromReadFile(requestHeader.requestLine().url());
        return buildHttpOkResponse(body, requestHeader.requestLine().contentTypeText());
    }
}
