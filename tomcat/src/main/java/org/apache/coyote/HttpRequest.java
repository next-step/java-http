package org.apache.coyote;

import org.apache.http.HttpMethod;
import org.apache.http.body.HttpBody;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.session.HttpSession;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests
 */
public class HttpRequest {
    protected final HttpRequestLine requestLine;
    protected final HttpRequestHeaders headers;
    protected final HttpBody body;
    protected final Function<Boolean, HttpSession> getSession;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeaders headers, HttpBody body, Function<Boolean, HttpSession> getSession) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
        this.getSession = getSession;
    }

    public String path() {
        return requestLine.path.value();
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

    public HttpSession getSession(boolean canCreate) {
        return getSession.apply(canCreate);
    }
}
