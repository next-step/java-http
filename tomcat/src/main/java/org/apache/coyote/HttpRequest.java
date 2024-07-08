package org.apache.coyote;

import java.util.List;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_requests
 */
public class HttpRequest {
    public HttpRequestLine requestLine;
    // TODO Headers, Body 추가

    public HttpRequest(final List<String> httpMessageLines) {
        this.requestLine = new HttpRequestLine(httpMessageLines.get(0));
    }
}
