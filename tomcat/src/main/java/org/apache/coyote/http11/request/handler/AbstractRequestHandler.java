package org.apache.coyote.http11.request.handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

abstract class AbstractRequestHandler implements RequestHandler {
    private static final String STATIC_PATH = "static";

    protected String buildBodyFromReadFile(final String pathString) throws IOException {
        final URL path = getClass().getClassLoader().getResource(STATIC_PATH + pathString);
        final File html = new File(path.getFile());

        return new String(Files.readAllBytes(html.toPath()));
    }

    protected String buildHttpOkResponse(final String body, final String contentType) {
        return String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: " + contentType + ";charset=utf-8 ",
                "Content-Length: " + body.getBytes().length + " ",
                "",
                body);
    }

    protected String buildRedirectResponse(final String location) {
        return String.join("\r\n",
                "HTTP/1.1 302 FOUND ",
                "Location: " + location + " ",
                "",
                "");
    }
}
