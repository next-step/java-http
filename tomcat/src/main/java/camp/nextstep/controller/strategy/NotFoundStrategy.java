package camp.nextstep.controller.strategy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.header.Http11ResponseHeader.HttpResponseHeaderBuilder;
import org.apache.coyote.http11.response.statusline.ProtocolVersion;
import org.apache.coyote.http11.response.statusline.StatusCode;

public class NotFoundStrategy implements RequestMethodStrategy {
    private final String NOTFOUND = "/static/404.html";

    @Override
    public boolean matched(HttpRequest httpRequest) {
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

            Http11ResponseHeader responseHeader = HttpResponseHeaderBuilder.builder()
                .contentType(ContentType.html.name())
                .contentLength(content.length)
                .build();

            return new Http11Response.HttpResponseBuilder()
                .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.NOTFOUND.name())
                .responseHeader(responseHeader)
                .messageBody(content)
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
