package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.constant.ContentType;

import java.io.IOException;

public class NotFoundHandler extends AbstractRequestHandler {

    private static final String NOT_FOUND_BODY = "Hello world!";

    @Override
    public String handle(final HttpRequest request) throws IOException {
        return buildNotFoundResponse(NOT_FOUND_BODY, ContentType.TEXT_HTML.contentType());
    }

    private String buildNotFoundResponse(final String body, final String contentsType) {
        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: " + contentsType + ";charset=utf-8 ",
                "Content-Length: " + body.getBytes().length + " ",
                "",
                body);
    }
}
