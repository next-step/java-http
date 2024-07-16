package org.apache.coyote.http11;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private final HttpProtocol httpProtocol;
    private final HttpStatusCode httpStatusCode;
    private final ContentType contentType;
    private final Charset charset;
    private final ResponseBody responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, ContentType contentType, ResponseBody responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatusCode = httpStatusCode;
        this.contentType = contentType;
        this.charset = StandardCharsets.UTF_8;
        this.responseBody = responseBody;
    }

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, ContentType contentType) {
        this(httpProtocol, httpStatusCode, contentType, ResponseBody.EMPTY);
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
        return "Content-Length: " + responseBody.length();
    }

    private String content() {
        return responseBody.toString();
    }


}
