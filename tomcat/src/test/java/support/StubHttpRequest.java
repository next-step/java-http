package support;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpRequestLine;
import org.apache.http.HttpPath;
import org.apache.http.body.HttpFormBody;
import org.apache.http.header.Cookie;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.session.HttpSession;
import org.apache.http.session.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class StubHttpRequest extends HttpRequest {
    private static final SessionManager sessionManager = new SessionManager();
    private static Function<Boolean, HttpSession> createSession = (c) -> {
        if(c) return sessionManager.create();
        else return null;
    };


    public StubHttpRequest() {
        super(new HttpRequestLine("GET /index.html HTTP/1.1 "), new HttpRequestHeaders(), null, createSession);
    }

    public StubHttpRequest(final HttpPath path) {
        super(new HttpRequestLine("GET " + path + " HTTP/1.1 "), new HttpRequestHeaders(), null, createSession);
    }

    public StubHttpRequest(final String account, final String password) {
        super(
                new HttpRequestLine("POST /login HTTP/1.1"),
                new HttpRequestHeaders(List.of("Content-Type: application/x-www-form-urlencoded", "Content-Length: " + ("account=" + account + "&password=" + password).getBytes().length)),
                new HttpFormBody("account=" + account + "&password=" + password),
                createSession
        );
    }

    public StubHttpRequest(final String account, final String password, final String email) {
        super(
                new HttpRequestLine("POST /register HTTP/1.1"),
                new HttpRequestHeaders(List.of("Content-Type: application/x-www-form-urlencoded", "Content-Length: " + ("account=" + account + "&password=" + password + "&email=" + email).getBytes().length)),
                new HttpFormBody("account=" + account + "&password=" + password + "&email=" + email),
                createSession
        );
    }

    public StubHttpRequest(final Cookie cookie) {
        super(
                new HttpRequestLine("POST /login HTTP/1.1"),
                new HttpRequestHeaders(Collections.singletonList(cookie.toString())),
                null,
                (c) -> {
                    var session = sessionManager.findSession(cookie.findSession());
                    if (session != null) {
                        return session;
                    }
                    return createSession.apply(c);
                }
        );
    }
    @Override
    public String toString() {
        return String.join("\r\n", requestLine.toString(), headers != null ? headers.toString() : "", "", body != null ? body.toString() : "");
    }
}
