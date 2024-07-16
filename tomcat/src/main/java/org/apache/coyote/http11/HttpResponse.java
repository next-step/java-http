package org.apache.coyote.http11;


import java.nio.charset.Charset;

public class HttpResponse {

    private final HttpProtocol httpProtocol;
    private final HttpStatusCode httpStatusCode;
    private final ContentType contentType;
    private final Charset charset;
    private final byte[] contentBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, ContentType contentType, Charset charset, byte[] contentBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatusCode = httpStatusCode;
        this.contentType = contentType;
        this.charset = charset;
        this.contentBody = contentBody;
    }

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, ContentType contentType, Charset utf8) {
        this(httpProtocol, httpStatusCode, contentType, utf8, "".getBytes());
    }

    public String generateMessage() {

        var sb = new StringBuilder();
        sb.append(firstLine()).append(" ").append("\r\n");
        sb.append(header()).append(" ").append("\r\n");
        sb.append(contentInfo()).append(" ").append("\r\n");
        sb.append("\r\n");
        sb.append(content());

        return sb.toString();
    }

    private String firstLine() {
        return httpProtocol.description() + " " + httpStatusCode.getCode() + " " + httpStatusCode.getDescription();
    }

    private String header() {
        return "Content-Type: " +  contentType.getDescription() + ";charset=" + charset.name().toLowerCase();
    }

    private String contentInfo() {
        return "Content-Length: " + contentBody.length;
    }

    private String content() {
        return new String(contentBody, charset);
    }


}
