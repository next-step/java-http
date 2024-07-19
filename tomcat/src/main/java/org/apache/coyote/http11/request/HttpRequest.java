package org.apache.coyote.http11.request;

import org.apache.catalina.Session;

import java.util.List;

public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private final MessageBody messageBody;
    private Session session;

    public HttpRequest(RequestLine requestLine, HttpRequestHeaders requestHeaders, MessageBody messageBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.messageBody = messageBody;
    }

    public HttpRequest(RequestLine requestLine, HttpRequestHeaders requestHeaders) {
        this(requestLine, requestHeaders, null);
    }

    public HttpRequest(RequestLine requestLine) {
        this(requestLine, new HttpRequestHeaders(List.of()));
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpRequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public MessageBody getRequestBody() {
        return messageBody;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
