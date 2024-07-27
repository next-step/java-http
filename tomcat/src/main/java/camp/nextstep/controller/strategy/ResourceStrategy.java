package camp.nextstep.controller.strategy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import org.apache.coyote.http11.exception.StaticResourceNotFoundException;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;

public class ResourceStrategy implements RequestMethodStrategy {

    public static final String ROOT_PATH = "/";
    private static final String BASE_DIR = "static";
    private static final String DEFAULT_URL = BASE_DIR + "/index.html";
    public static final String PERIOD = ".";

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
            .equals(RequestMethod.GET.name())
            && httpRequest.getParams().isEmpty();
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        String url = addStaticDir(httpRequest);

        try {
            final URL resource = getClass()
                .getClassLoader()
                .getResource(url);
            final File file = new File(Objects.requireNonNull(resource).getFile());
            final Path path = file.toPath();
            final byte[] content = Files.readAllBytes(path);

            final ContentType extension =
                Arrays.stream(ContentType.values())
                    .filter(ext -> file.getName().endsWith(ext.name()))
                    .findFirst().orElseGet(() -> ContentType.all);

            final Http11ResponseHeader http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                .builder()
                .contentLength(content.length)
                .contentType(extension.name())
                .build();

            return new Http11Response.HttpResponseBuilder()
                .responseHeader(http11ResponseHeader)
                .statusLine(httpRequest.getVersion(), "OK")
                .messageBody(content)
                .build();

        } catch (IOException e) {
            throw new StaticResourceNotFoundException("Static Resource가 없습니다.");
        }
    }

    private String addStaticDir(HttpRequest httpRequest) {
        String url = new StringBuilder()
            .append(BASE_DIR)
            .append(httpRequest.getRequestUrl())
            .toString();

        if (httpRequest.getRequestUrl().equals(ROOT_PATH)) {
            url = DEFAULT_URL;
        }

        if (!httpRequest.getRequestUrl().contains(PERIOD)) {
            url = url + ".html";
        }

        return url;
    }

}
