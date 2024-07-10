package org.apache.coyote.handler;

import org.apache.coyote.*;
import org.apache.http.body.HttpTextBody;

public class DefaultHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        final var responseBody = new HttpTextBody("Hello world!");

        return new HttpResponse(responseBody);
    }

}
