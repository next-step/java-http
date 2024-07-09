package support;

import org.apache.coyote.HttpRequest;
import org.apache.http.HttpMethod;
import org.apache.http.HttpPath;

import java.util.Collections;

public class StubHttpRequest extends HttpRequest {
    public StubHttpRequest() {
        super(Collections.singletonList("GET /index.html HTTP/1.1 "));
    }

    public StubHttpRequest(final String requestLine) {
        super(Collections.singletonList(requestLine));
    }

    public StubHttpRequest(final HttpMethod method, final HttpPath path) {
        super(Collections.singletonList(method + " " + path + " HTTP/1.1 "));
    }

    public StubHttpRequest(final HttpPath path) {
        super(Collections.singletonList("GET " + path + " HTTP/1.1 "));
    }
}
