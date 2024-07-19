package org.apache.coyote.http11.response;


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
        sb.append(statusLine()).append(" ").append("\r\n");
        sb.append(httpResponseHeaders.toMessage());
        sb.append(contentInfo()).append(" ").append("\r\n");
        sb.append("\r\n");
        sb.append(content());

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

    private String statusLine() {
        return statusLine.generateMessage();
    }


    private String contentInfo() {
        return messageBody.toContentLengthHeaderMessage();
    }


    private String content() {
        return messageBody.toString();
    }
}
