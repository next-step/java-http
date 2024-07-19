package org.apache.coyote.http11.response;


import org.apache.coyote.http11.constants.HttpFormat;

public class HttpResponse {

    private StatusLine statusLine;
    private HttpResponseHeaders httpResponseHeaders;
    private MessageBody messageBody;

    public HttpResponse(StatusLine statusLine, HttpResponseHeaders httpResponseHeaders, MessageBody messageBody) {
        this.statusLine = statusLine;
        this.httpResponseHeaders = httpResponseHeaders;
        this.messageBody = messageBody;
    }

    public HttpResponse(StatusLine statusLine, HttpResponseHeaders httpResponseHeaders) {
        this(statusLine, httpResponseHeaders, MessageBody.EMPTY);
    }

    public HttpResponse(StatusLine statusLine) {
        this.statusLine = statusLine;
    }


    public String generateMessage() {

        var sb = new StringBuilder();
        sb.append(statusLine.toMessage());
        sb.append(httpResponseHeaders.toMessage());
        if (messageBody != null) {
            sb.append(messageBody.toContentLengthMessage());
        }
        sb.append(HttpFormat.CRLF);
        if (messageBody != null) {
            sb.append(messageBody);
        }

        return sb.toString();
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public void setHttpResponseHeaders(HttpResponseHeaders httpResponseHeaders) {
        this.httpResponseHeaders = httpResponseHeaders;
    }

    public void setResponseBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }


}
