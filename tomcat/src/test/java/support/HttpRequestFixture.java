package support;

import org.apache.coyote.http11.HttpHeader;
import org.apache.coyote.http11.HttpHeaders;
import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.HttpRequest;
import org.apache.session.SessionManager;

import java.util.List;

public class HttpRequestFixture {
    private HttpRequestFixture() {
    }

    public static HttpRequest createBy(HttpMethod httpMethod, String path, String requestBody) {
        String httpRequestMessage = String.join("\r\n",
                "%s %s HTTP/1.1 ".formatted(httpMethod, path),
                "",
                "%s".formatted(requestBody)
        );

        return HttpRequest.of(httpRequestMessage, SessionManager.create());
    }

    public static HttpRequest createBy(HttpMethod httpMethod, String path, String requestBody, HttpHeader... httpHeaders) {
        String httpRequestMessage = String.join("\r\n",
                "%s %s HTTP/1.1 ".formatted(httpMethod, path),
                "%s".formatted(HttpHeaders.from(List.of(httpHeaders)).createMessage()),
                "",
                "%s".formatted(requestBody)
        );

        return HttpRequest.of(httpRequestMessage, SessionManager.create());
    }
}
