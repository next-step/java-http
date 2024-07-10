package org.apache.coyote;

import org.apache.http.HttpPath;
import org.apache.http.HttpStatus;
import org.apache.http.body.HttpBody;
import org.apache.http.header.HttpRequestHeaders;
import org.apache.http.header.HttpResponseHeaders;
import org.apache.http.header.Location;
import org.apache.http.header.SetCookie;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_responses
 */
public class HttpResponse {
    private final static String DELIMITER = "\r\n";

    private final HttpResponseStatusLine statusLine;
    private final HttpResponseHeaders headers;
    private final HttpBody body;

    public HttpResponse(HttpBody body) {
        this.statusLine = new HttpResponseStatusLine(HttpStatus.OK);
        this.headers = body.addContentHeader(new HttpResponseHeaders());
        this.body = body;
    }

    public HttpResponse(HttpStatus status, HttpResponseHeaders headers) {
        this.statusLine = new HttpResponseStatusLine(status);
        this.headers = headers;
        this.body = null;
    }

    public HttpResponse(final HttpPath path) {
        this(HttpStatus.Found, new HttpResponseHeaders().add(new Location(path)));
    }

    HttpResponse(HttpResponseStatusLine statusLine, HttpResponseHeaders headers, HttpBody body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpResponse addCookie(String cookie) {
        return new HttpResponse(statusLine, headers.add(new SetCookie(cookie)), body);
    }

    @Override
    public String toString() {
        if (body != null) {
            return String.join(DELIMITER, statusLine.toString(), headers.toString(), "", body.toString());
        } else {
            return statusLine + DELIMITER + headers;
        }
    }
}
