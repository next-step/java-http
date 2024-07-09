package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.model.HttpRequestHeader;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.http11.model.constant.HttpStatusCode;

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

    protected String buildOkHttpResponse(final HttpRequestHeader httpRequestHeader, final String body) {
        return new HttpResponse(HttpStatusCode.OK, httpRequestHeader, body)
                .buildOkResponse();
    }

    protected String buildRedirectHttpResponse(final HttpRequestHeader httpRequestHeader, final String location) {
        return new HttpResponse(HttpStatusCode.FOUND, httpRequestHeader)
                .buildRedirectResponse(location);
    }

    protected String buildRedirectSetCookieHttpResponse(final HttpRequestHeader httpRequestHeader, final String location, final String cookie) {
        return new HttpResponse(HttpStatusCode.FOUND, httpRequestHeader)
                .buildRedirectSetCookieResponse(location, cookie);
    }
}
