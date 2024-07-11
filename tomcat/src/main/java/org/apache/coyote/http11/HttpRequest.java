package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class HttpRequest {
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int HEADER_START_INDEX = 1;
    private static final String CRLF = "\r\n";

    private final RequestLine requestLine;
    private final HttpHeaders httpHeaders;
    private final RequestBody requestBody;

    private HttpRequest(final RequestLine requestLine, final HttpHeaders httpHeaders, final RequestBody requestBody) {
        this.requestLine = requestLine;
        this.httpHeaders = httpHeaders;
        this.requestBody = requestBody;
    }

    public static HttpRequest from(final String httpRequestMessage) {
        String[] httpRequestMessages = httpRequestMessage.split(CRLF);

        List<HttpHeader> httpHeaders = Arrays.stream(httpRequestMessages, HEADER_START_INDEX, httpRequestMessages.length)
                .takeWhile(Predicate.not(String::isBlank))
                .map(HttpHeader::from)
                .toList();

        return new HttpRequest(
                RequestLine.from(httpRequestMessages[REQUEST_LINE_INDEX]),
                HttpHeaders.from(httpHeaders),
                RequestBody.empty()
        );
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpProtocol getProtocol() {
        return requestLine.getProtocol();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public String getHeaderValue(final String name) {
        return httpHeaders.getHeaderValue(name);
    }

    public void addRequestBody(final String body) {
        requestBody.addBody(body);
    }

    public String getBodyValue(final String key) {
        return requestBody.getValue(key);
    }
}