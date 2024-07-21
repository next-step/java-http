package org.apache.coyote.http11.controller.strategy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.ProtocolVersion;
import org.apache.coyote.http11.response.StatusCode;

public class NotFoundStrategy implements RequestMethodStrategy {
    private final String NOTFOUND = "/static/404.html";

    @Override
    public boolean matched(String requestMethod) {
        return false;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        try {
            final URL resource = getClass()
                .getClassLoader()
                .getResource(NOTFOUND);
            final File file = new File(Objects.requireNonNull(resource).getFile());
            final Path path = file.toPath();
            final byte[] content = Files.readAllBytes(path);

            return new Http11Response.HttpResponseBuilder()
                .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.NOTFOUND.name())
                .responseHeader(ContentType.html.getContentType(), content.length)
                .messageBody(content)
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
