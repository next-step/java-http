package org.apache.coyote.http11.response;


import org.apache.coyote.http11.HttpProtocol;

public class HttpResponse {

    private final HttpProtocol httpProtocol;
    private final HttpStatusCode httpStatusCode;
    private final HttpResponseHeaders httpResponseHeaders;
    private final ResponseBody responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, HttpResponseHeaders httpResponseHeaders, ResponseBody responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatusCode = httpStatusCode;
        this.httpResponseHeaders = httpResponseHeaders;
        this.responseBody = responseBody;
    }

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, HttpResponseHeaders httpResponseHeaders) {
        this(httpProtocol, httpStatusCode, httpResponseHeaders, ResponseBody.EMPTY);
    }


    public String generateMessage() {

        var sb = new StringBuilder();
        sb.append(firstLine()).append(" ").append("\r\n");
        sb.append(httpResponseHeaders.toMessage());
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
