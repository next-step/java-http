package org.apache.coyote;

import org.apache.http.HttpMethod;
import org.apache.http.HttpPath;
import org.apache.http.body.HttpBody;
import org.apache.http.header.HttpHeaders;

import java.util.List;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests
 */
public class HttpRequest {
    protected final HttpRequestLine requestLine;
    protected final HttpHeaders headers;
    protected final HttpBody body;

    public HttpRequest(HttpRequestLine requestLine, HttpHeaders headers, HttpBody body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
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
}
