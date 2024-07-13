package org.apache.coyote.handler;

import org.apache.coyote.HttpResponse;
import org.apache.coyote.handler.NotSupportHandlerException;
import org.apache.file.FileReader;
import org.apache.http.body.HttpFileBody;

import java.io.IOException;
import java.nio.file.Path;

public class ResourceHandler {

    public HttpResponse handle(Path path) {
        try {
            final var file = FileReader.read(path);
            final var body = new HttpFileBody(file);

            return new HttpResponse(body);
        } catch (IOException e) {
            throw new NotSupportHandlerException();
        }
    }
}
