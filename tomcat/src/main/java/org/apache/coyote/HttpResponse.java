package org.apache.coyote;

import org.apache.http.HttpStatus;
import org.apache.http.body.HttpBody;
import org.apache.http.header.HttpHeaders;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_responses
 */
public class HttpResponse {
    private final static String DELIMITER = "\r\n";

    private final HttpResponseStatusLine statusLine;
    private final HttpHeaders headers;
    private final HttpBody body;

    public HttpResponse(HttpStatus status, HttpHeaders headers, HttpBody body) {
        this.statusLine = new HttpResponseStatusLine(status);
        this.headers = headers;
        this.body = body;
    }

    public HttpResponse(HttpHeaders headers, HttpBody body) {
        this.statusLine = new HttpResponseStatusLine(HttpStatus.OK);
        this.headers = body.addContentHeader(headers);
        ;
        this.body = body;
    }

    public HttpResponse(HttpBody body) {
        this.statusLine = new HttpResponseStatusLine(HttpStatus.OK);
        this.headers = body.addContentHeader(new HttpHeaders());
        this.body = body;
    }

    public HttpResponse(HttpStatus status, HttpHeaders headers) {
        this.statusLine = new HttpResponseStatusLine(status);
        this.headers = headers;
        this.body = null;
    }

    @Override
    public String toString() {
        if (body != null) {
            return String.join(DELIMITER, statusLine.toString(), headers.toString(), "", body.toString());
        } else {
            return String.join(DELIMITER, statusLine.toString(), headers.toString());
        }
    }
}
