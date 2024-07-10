package org.apache.coyote.handler;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;
import org.apache.file.FileReader;
import org.apache.http.body.HttpFileBody;

public class ResourceHandler implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        try {
            final var file = FileReader.read(request.path());
            final var body = new HttpFileBody(file);

            return new HttpResponse(body);
        } catch (Exception e) {
            throw new NotSupportHandlerException();
        }
    }
}
