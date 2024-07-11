package org.apache.coyote;

import org.apache.http.HttpMethod;
import org.apache.http.HttpPath;
import org.apache.http.body.HttpBody;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.session.HttpSession;
import org.apache.http.session.SessionManager;

import java.util.UUID;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests
 */
public class HttpRequest {
    private final SessionManager sessionManager = new SessionManager();
    protected final HttpRequestLine requestLine;
    protected final HttpRequestHeaders headers;
    protected final HttpBody body;
    protected final HttpSession session;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeaders headers, HttpBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.session = headers.getSession();
    }

    public boolean matchPath(final String path) {
        return new HttpPath(path).equals(requestLine.path);
    }

    public String path() {
        return requestLine.path.value();
    }

    public String getParam(final String key) {
        return requestLine.params.get(key);
    }

    public boolean isGet() {
        return requestLine.method == HttpMethod.GET;
    }

    public String getBodyValue(final String key) {
        if (body == null) {
            return null;
        }
        return body.getValue(key);
    }

    public HttpSession getSession(Boolean canCreate) {
        if (this.session != null) {
            return this.session;
        }

        if (!canCreate) {
            return null;
        }

        var id = UUID.randomUUID().toString();
        var session = new HttpSession(id);
        sessionManager.add(session);
        return session;
    }
}
