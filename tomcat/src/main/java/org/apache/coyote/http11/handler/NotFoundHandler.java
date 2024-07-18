package org.apache.coyote.http11.handler;

import org.apache.coyote.http11.RequestHandler;
import org.apache.coyote.http11.ResourceFinder;
import org.apache.coyote.http11.constants.HttpStatus;
import org.apache.coyote.http11.model.ContentType;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;

import java.io.IOException;
import java.net.URL;

public class NotFoundHandler implements RequestHandler {

    private static final String RESOURCE_PATH = "/404.html";

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContent(getContent());
        response.setContentType(ContentType.TEXT_HTML);
        response.send();
    }

    private String getContent() throws IOException {
        final URL resource = ResourceFinder.findResource(RESOURCE_PATH);
        return ResourceFinder.findContent(resource);
    }

}
