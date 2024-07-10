package support;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpRequestLine;
import org.apache.http.HttpPath;
import org.apache.http.body.HttpFormBody;
import org.apache.http.header.HttpHeaders;

import java.util.List;

public class StubHttpRequest extends HttpRequest {
    public StubHttpRequest() {
        super(new HttpRequestLine("GET /index.html HTTP/1.1 "), null, null);
    }

    public StubHttpRequest(final HttpPath path) {
        super(new HttpRequestLine("GET " + path + " HTTP/1.1 "), null, null);
    }

    public StubHttpRequest(final String account, final String password) {
        super(
                new HttpRequestLine("POST /login HTTP/1.1"),
                new HttpHeaders(List.of("Content-Type: application/x-www-form-urlencoded", "Content-Length: " + ("account=" + account + "&password=" + password).getBytes().length)),
                new HttpFormBody("account=" + account + "&password=" + password)
        );
    }

    public StubHttpRequest(final String account, final String password, final String email) {
        super(
                new HttpRequestLine("POST /register HTTP/1.1"),
                new HttpHeaders(List.of("Content-Type: application/x-www-form-urlencoded", "Content-Length: " + ("account=" + account + "&password=" + password + "&email=" + email).getBytes().length)),
                new HttpFormBody("account=" + account + "&password=" + password + "&email=" + email)

        );
    }

    @Override
    public String toString() {
        return String.join("\r\n", requestLine.toString(), headers != null ? headers.toString() : "", "", body != null ? body.toString() : "");
    }
}
