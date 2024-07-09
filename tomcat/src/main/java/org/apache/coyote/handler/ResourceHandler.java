package org.apache.coyote.handler;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.http.header.ContentLength;
import org.apache.http.header.ContentType;
import org.apache.http.header.HttpHeaders;
import org.apache.http.header.MediaType;

import java.io.File;
import java.nio.file.Files;

public class ResourceHandler implements Handler {
    private static final String DEFAULT_PATH = "static";

    @Override
    public HttpResponse handle(HttpRequest request) {
        try {
            final var resource = getClass().getClassLoader().getResource(request.resolveStringPath(DEFAULT_PATH));
            final var path = new File(resource.getFile()).toPath();
            final var body = new String(Files.readAllBytes(path));

            final var headers = new HttpHeaders()
                    .add(new ContentType(new MediaType(path)))
                    .add(new ContentLength(body.getBytes().length));

            return new HttpResponse(headers, body);
        } catch (Exception e) {
            throw new NotSupportHandlerException();
        }
    }
}
