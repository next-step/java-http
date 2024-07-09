package org.apache.coyote;

import org.apache.http.header.HttpHeaders;

import java.util.ArrayList;
import java.util.Map;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_responses
 */
public class HttpResponse {
    private final static String DELIMITER = "\r\n";

    private HttpResponseStatusLine statusLine;
    private HttpHeaders headers;
    private String body;

    public HttpResponse(HttpResponseStatusLine statusLine, HttpHeaders headers, String body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpResponse(HttpHeaders headers, String body) {
        this.statusLine = HttpResponseStatusLine.HTTP_11_OK;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.join(DELIMITER, statusLine.toString(), headers.toString(), "", body);
    }
}
