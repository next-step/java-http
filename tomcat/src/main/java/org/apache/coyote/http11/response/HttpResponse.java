package org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.HttpRequest;

import java.io.IOException;

public class HttpResponse {
    private final StatusLine statusLine;
    private final ResponseHeaders headers;
    private final ResponseBody body;

    private HttpResponse(StatusLine statusLine, ResponseHeaders headers, ResponseBody body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse responseOk(HttpRequest httpRequest) throws IOException {
        StatusLine statusLine = StatusLine.create("HTTP/1.1", StatusCode.OK);
        ResponseHeaders headers = ResponseHeaders.create(httpRequest);
        ResponseBody body = ResponseBody.create(httpRequest.getUrlPath());
        return new HttpResponse(statusLine, headers, body);
    }

}
