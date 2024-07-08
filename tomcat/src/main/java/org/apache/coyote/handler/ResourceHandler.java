package org.apache.coyote.handler;

import org.apache.coyote.*;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

public class ResourceHandler implements Handler {
    private static final String DEFAULT_PATH = "static";

    @Override
    public HttpResponse handle(HttpRequest request) {
        try {
            final var resource = getClass().getClassLoader().getResource(DEFAULT_PATH + request.requestLine.path);
            final var path = new File(resource.getFile()).toPath();
            final var body = new String(Files.readAllBytes(path));
            final var statusLine = new HttpResponseStatusLine(Protocol.HTTP.name(), Protocol.HTTP.version, HttpStatus.OK.statusCode, HttpStatus.OK.name());

            final var responseHeaders = Map.of(HttpHeaders.CONTENT_LENGTH, Integer.toString(body.getBytes().length));

            final var fileType = Files.probeContentType(path);
            final var representationHeaders = Map.of(HttpHeaders.CONTENT_TYPE, fileType + ";charset=utf-8");

            return new HttpResponse(statusLine, responseHeaders, representationHeaders, body);
        } catch (Exception e) {
            throw new NotSupportHandlerException();
        }
    }
}
