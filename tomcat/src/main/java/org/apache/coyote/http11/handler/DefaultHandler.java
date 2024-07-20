package org.apache.coyote.http11.handler;

import org.apache.coyote.http11.RequestHandler;
import org.apache.coyote.http11.constants.HttpStatus;
import org.apache.coyote.http11.model.ContentType;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;

import java.io.IOException;

public class DefaultHandler implements RequestHandler {

    public static final String DEFAULT_CONTENT = "Hello World!";

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        response.setStatus(HttpStatus.SUCCESS);
        response.setContent(DEFAULT_CONTENT);
        response.setContentType(ContentType.TEXT_HTML);
        response.send();
    }
}
