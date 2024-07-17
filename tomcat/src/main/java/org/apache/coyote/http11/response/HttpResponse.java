package org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.HttpRequest;

import java.io.IOException;
import java.util.Map;

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
        ResponseBody body = ResponseBody.create(httpRequest.getUrlPath());
        ResponseHeaders headers = ResponseHeaders.create(httpRequest, body);
        return new HttpResponse(statusLine, headers, body);
    }

    public static HttpResponse responseOkWithOutHtml(HttpRequest httpRequest) throws IOException {
        StatusLine statusLine = StatusLine.create("HTTP/1.1", StatusCode.OK);
        ResponseBody body = ResponseBody.create(httpRequest.getUrlPath() + ".html");
        ResponseHeaders headers = ResponseHeaders.create(httpRequest, body);
        return new HttpResponse(statusLine, headers, body);
    }

    public static HttpResponse redirect(HttpRequest httpRequest, String redirectPath) throws IOException {
        StatusLine statusLine = StatusLine.create("HTTP/1.1", StatusCode.FOUND);
        ResponseBody body = ResponseBody.create(redirectPath);
        ResponseHeaders headers = ResponseHeaders.create(httpRequest, body);
        return new HttpResponse(statusLine, headers, body);
    }

    public static HttpResponse redirectRoot(HttpRequest httpRequest) throws IOException {
        StatusLine statusLine = StatusLine.create("HTTP/1.1", StatusCode.FOUND);
        ResponseBody body = ResponseBody.create("/index.html");
        ResponseHeaders headers = ResponseHeaders.create(httpRequest, body);
        return new HttpResponse(statusLine, headers, body);
    }

    public static HttpResponse responseUnAuthorized(HttpRequest httpRequest) throws IOException {
        StatusLine statusLine = StatusLine.create("HTTP/1.1", StatusCode.UNAUTHORIZED);
        ResponseBody body = ResponseBody.create("/401.html");
        ResponseHeaders headers = ResponseHeaders.create(httpRequest, body);
        return new HttpResponse(statusLine, headers, body);
    }

    public static HttpResponse responseNotFound(HttpRequest httpRequest) throws IOException {
        StatusLine statusLine = StatusLine.create("HTTP/1.1", StatusCode.NOT_FOUND);
        ResponseBody body = ResponseBody.create("/404.html");
        ResponseHeaders headers = ResponseHeaders.create(httpRequest, body);
        return new HttpResponse(statusLine, headers, body);
    }

    public String getResponseBody() {
        return body.getResponseBody();
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }
}
