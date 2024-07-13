package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import org.apache.coyote.http11.meta.HttpCookie;
import org.apache.coyote.http11.meta.HttpHeader;

public class Request {

    private final RequestLine requestLine;
    private final HttpHeader requestHeader;
    private final HttpCookie cookies;
    private final RequestBody requestBody;

    public Request(BufferedReader br) throws IOException {
        this.requestLine = RequestLine.from(br.readLine());
        this.requestHeader = HttpHeader.from(br);
        this.cookies = HttpCookie.from(requestHeader.getCookies());
        this.requestBody = RequestBody.from(br, requestHeader.getContentLength());
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public HttpCookie getCookies() {
        return cookies;
    }
}
