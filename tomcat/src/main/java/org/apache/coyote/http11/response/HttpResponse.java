package org.apache.coyote.http11.response;


import jakarta.HttpServletResponse;
import org.apache.coyote.http11.constants.HttpFormat;

public class HttpResponse {

    private StatusLine statusLine;
    private String host;
    private HttpResponseHeaders httpResponseHeaders = new HttpResponseHeaders();
    private MessageBody messageBody;

    public HttpResponse(StatusLine statusLine, HttpResponseHeaders httpResponseHeaders, MessageBody messageBody) {
        this.statusLine = statusLine;
        this.httpResponseHeaders = httpResponseHeaders;
        this.messageBody = messageBody;
    }

    public HttpResponse(StatusLine statusLine, HttpResponseHeaders httpResponseHeaders) {
        this(statusLine, httpResponseHeaders, MessageBody.EMPTY);
    }

    public HttpResponse(StatusLine statusLine, String host) {
        this.statusLine = statusLine;
        this.host = host;
    }


    public void update(HttpServletResponse httpServletResponse) {
        this.statusLine = new StatusLine(statusLine.protocol(), httpServletResponse.getStatusCode());
        this.messageBody = httpServletResponse.getMessageBody() != null ? httpServletResponse.getMessageBody() : MessageBody.EMPTY;

        if (httpServletResponse.getCookie() != null) {
            this.httpResponseHeaders.addCookie(httpServletResponse.getCookie());
        }

        if (httpServletResponse.getMimeType() != null) {
            this.httpResponseHeaders.addMimeType(httpServletResponse.getMimeType());
        }

        if (httpServletResponse.getRedirectPath() != null) {
            this.httpResponseHeaders.addLocation(Location.of(statusLine.protocol(), host, httpServletResponse.getRedirectPath()));
        }
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


}
