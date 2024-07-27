package camp.nextstep.controller.strategy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.apache.coyote.http11.exception.StaticResourceNotFoundException;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.statusline.ProtocolVersion;
import org.apache.coyote.http11.response.statusline.StatusCode;

public class UnauthorizedStrategy implements RequestMethodStrategy {

    private static final String BASE_DIR = "static";
    private static final String DEFAULT_URL = BASE_DIR + "/401.html";

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return false;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        try {

            final URL resource = getClass()
                .getClassLoader()
                .getResource(DEFAULT_URL);

            final File file = new File(Objects.requireNonNull(resource).getFile());
            final Path path = file.toPath();
            final byte[] content = Files.readAllBytes(path);

            final Http11ResponseHeader http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                .builder()
                .contentLength(content.length)
                .contentType(ContentType.html.name())
                .build();

            return new Http11Response.HttpResponseBuilder()
                .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.UNAUTHORIZED.name())
                .responseHeader(http11ResponseHeader)
                .messageBody(content)
                .build();

        } catch (
            IOException e) {
            throw new StaticResourceNotFoundException("Static Resource가 없습니다.");
        }
    }
}
