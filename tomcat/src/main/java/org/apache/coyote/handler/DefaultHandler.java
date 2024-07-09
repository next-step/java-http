package org.apache.coyote.handler;

import org.apache.coyote.*;
import org.apache.http.HttpStatus;
import org.apache.http.HttpProtocol;
import org.apache.http.header.ContentLength;
import org.apache.http.header.ContentType;
import org.apache.http.header.HttpHeaders;
import org.apache.http.header.MediaType;

import java.util.Map;

public class DefaultHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        final var responseBody = "Hello world!";
        final var headers = new HttpHeaders()
                .add(new ContentType(MediaType.TEXT_HTML))
                .add(new ContentLength(responseBody.getBytes().length));

        return new HttpResponse(headers, responseBody);
    }

}
