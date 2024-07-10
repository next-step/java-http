package org.apache.coyote;

import org.apache.http.HttpPath;

import java.util.List;
import java.util.Optional;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests
 */
public class HttpRequest {
    private final HttpRequestLine requestLine;
    // TODO Headers, Body 추가

    public HttpRequest(final List<String> httpMessageLines) {
        this.requestLine = new HttpRequestLine(httpMessageLines.get(0));
    }

    public boolean matchPath(final String path) {
        return new HttpPath(path).equals(requestLine.path);
    }

    public String path() {
        return requestLine.path.value();
    }

    public Optional<String> getParam(final String key) {
        return requestLine.params.get(key);
    }
}