package org.apache.coyote.http11.handler;

import org.apache.coyote.http11.RequestHandler;
import org.apache.coyote.support.ResourceFinder;
import org.apache.coyote.http11.constants.HttpStatus;
import org.apache.coyote.http11.model.ContentType;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.support.FileUtils;

import java.io.IOException;
import java.net.URL;

public class StaticResourceHandler implements RequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        response.setStatus(HttpStatus.SUCCESS);
        response.setContent(getContent(request.getHttpPath()));
        final String extension = FileUtils.extractExtension(request.getHttpPath());
        response.setContentType(ContentType.fromExtension(extension));
        response.send();
    }

    private String getContent(String requestPath) throws IOException {
        final URL resource = ResourceFinder.findResource(requestPath);
        return ResourceFinder.findContent(resource);
    }

}
