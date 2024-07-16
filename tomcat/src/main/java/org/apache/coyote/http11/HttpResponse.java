package org.apache.coyote.http11;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private final HttpProtocol httpProtocol;
    private final HttpStatusCode httpStatusCode;
    private final HttpHeaders httpHeaders;
    private final ResponseBody responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, HttpHeaders httpHeaders, ResponseBody responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatusCode = httpStatusCode;
        this.httpHeaders = httpHeaders;
        this.responseBody = responseBody;
    }

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, HttpHeaders httpHeaders) {
        this(httpProtocol, httpStatusCode, httpHeaders, ResponseBody.EMPTY);
    }


    public String generateMessage() {

        var sb = new StringBuilder();
        sb.append(firstLine()).append(" ").append("\r\n");
        sb.append(httpHeaders.contentTypeField()).append(" ").append("\r\n");
        sb.append(contentInfo()).append(" ").append("\r\n");
        sb.append("\r\n");
        sb.append(content());

        return sb.toString();
    }

    private String firstLine() {
        return httpProtocol.description() + " " + httpStatusCode.getCode() + " " + httpStatusCode.getDescription();
    }


    private String contentInfo() {
        return "Content-Length: " + responseBody.length();
    }

    private String content() {
        return responseBody.toString();
    }


}
