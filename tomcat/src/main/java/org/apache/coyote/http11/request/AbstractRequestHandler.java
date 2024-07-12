package org.apache.coyote.http11.request;

import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.http11.model.constant.HttpStatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public abstract class AbstractRequestHandler implements RequestHandler {
    private static final String STATIC_PATH = "static";

    protected String buildBodyFromReadFile(final String pathString) throws IOException {
        final URL path = getClass().getClassLoader().getResource(STATIC_PATH + pathString);
        final File html = new File(path.getFile());

        return new String(Files.readAllBytes(html.toPath()));
    }

    protected String buildOkHttpResponse(final HttpHeaders httpHeaders, final String body) {
        return new HttpResponse(HttpStatusCode.OK, httpHeaders, body)
                .buildResponse();
    }

    protected String buildRedirectHttpResponse(final HttpHeaders httpHeaders, final String location) {
        final HttpResponse httpResponse = new HttpResponse(HttpStatusCode.FOUND, httpHeaders);
        httpResponse.addLocationHeader(location);

        return httpResponse.buildResponse();
    }

    protected String buildRedirectSetCookieHttpResponse(final HttpHeaders httpHeaders, final String location, final String cookie) {
        final HttpResponse httpResponse = new HttpResponse(HttpStatusCode.FOUND, httpHeaders);
        httpResponse.addLocationHeader(location);
        httpResponse.addJSessionId(cookie);

        return httpResponse.buildResponse();
    }
}
