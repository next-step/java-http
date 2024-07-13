package org.apache.coyote.handler;

import org.apache.coyote.*;
import org.apache.http.body.HttpTextBody;

public class DefaultHandler implements Handler {
    private static final String MESSAGE = "Hello world!";

    @Override
    public HttpResponse handle(HttpRequest request) {
        final var responseBody = new HttpTextBody(MESSAGE);

        return new HttpResponse(responseBody);
    }

}
