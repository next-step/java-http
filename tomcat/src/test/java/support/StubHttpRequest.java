package support;

import org.apache.coyote.HttpRequest;
import org.apache.http.HttpPath;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StubHttpRequest extends HttpRequest {
    public StubHttpRequest() {
        super(Collections.singletonList("GET /index.html HTTP/1.1 "));
    }

    public StubHttpRequest(final HttpPath path) {
        super(Collections.singletonList("GET " + path + " HTTP/1.1 "));
    }

    public StubHttpRequest(final String account, final String password) {
        super(List.of(
                "POST /login HTTP/1.1",
                "Content-Type: application/x-www-form-urlencoded",
                "",
                "account=" + account + "&password=" + password
        ));
    }

    public StubHttpRequest(final String account, final String password, final String email) {
        super(List.of(
                "POST /register HTTP/1.1",
                "Content-Type: application/x-www-form-urlencoded",
                "",
                "account=" + account + "&password=" + password + "&email=" + email
        ));
    }

    @Override
    public String toString() {
        return String.join("\r\n", requestLine.toString(), headers != null ? headers.toString() : "", "", body != null ? body.toString() : "");
    }
}
