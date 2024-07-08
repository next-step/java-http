package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.model.HttpRequestHeader;
import org.apache.coyote.http11.model.RequestLine;
import org.apache.coyote.http11.model.constant.HttpMethod;

import java.io.IOException;

public class RegisterHandler extends AbstractRequestHandler {

    private static final String POST_METHOD_REDIRECT_PATH = "/index.html";
    private static final String OTHER_METHOD_REDIRECT_PATH = "/404.html";

    @Override
    public String handle(final HttpRequestHeader requestHeader) throws IOException {
        final RequestLine requestLine = requestHeader.requestLine();
        final HttpMethod httpMethod = requestLine.httpMethod();

        if (httpMethod.isGetMethod()) {
            final String body = buildBodyFromReadFile(requestLine.url());
            return buildHttpOkResponse(body, requestLine.contentTypeText());
        }

        if (httpMethod.isPostMethod()) {
            return buildRedirectResponse(POST_METHOD_REDIRECT_PATH);
        }

        return buildRedirectResponse(OTHER_METHOD_REDIRECT_PATH);
    }
}
