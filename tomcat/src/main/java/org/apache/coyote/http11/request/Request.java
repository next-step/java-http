package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final HttpCookie cookies;
    private final RequestBody requestBody;

    public Request(BufferedReader br) throws IOException {
        this.requestLine = RequestLine.from(br.readLine());
        this.requestHeader = RequestHeader.from(br);
        this.cookies = HttpCookie.from(requestHeader.getCookies());
        this.requestBody = RequestBody.from(br, requestHeader.getContentLength());
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpCookie getCookies() {
        return cookies;
    }
}
