package support;

import org.apache.coyote.HttpRequest;
import org.apache.http.HttpPath;

import java.util.Collections;
import java.util.List;

public class StubHttpRequest extends HttpRequest {
    public StubHttpRequest() {
        super(Collections.singletonList("GET /index.html HTTP/1.1 "));
    }

    public StubHttpRequest(final HttpPath path) {
        super(Collections.singletonList("GET " + path + " HTTP/1.1 "));
    }

    public static HttpRequest login(final String account, final String password) {
        return new HttpRequest(List.of(
                "POST /login HTTP/1.1",
                "Content-Type: application/x-www-form-urlencoded",
                "",
                "account=" + account + "&password=" + password
        ));
    }
}
