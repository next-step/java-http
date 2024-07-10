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
    private static final String EMPTY_LINE = "";
    protected final HttpRequestLine requestLine;
    protected final HttpHeaders headers;
    protected final HttpBody body;

    public HttpRequest(final List<String> messages) {
        this.requestLine = new HttpRequestLine(messages.get(0));
        var emptyLine = messages.indexOf(EMPTY_LINE);
        if (emptyLine == -1) {
            this.headers = null;
            this.body = null;
        } else {
            this.headers = new HttpHeaders(messages.subList(1, emptyLine));
            this.body = headers.parseBody(String.join("\n", messages.subList(emptyLine + 1, messages.size())));
        }
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
