package org.apache.coyote.http11.request;

import org.apache.catalina.Session;


public class HttpRequest {

    private final RequestLine requestLine;
    private final HttpRequestHeaders requestHeaders;
    private MessageBody messageBody;
    private Session session;

    public HttpRequest(RequestLine requestLine, HttpRequestHeaders requestHeaders, MessageBody messageBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.messageBody = messageBody;
    }



    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpRequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
