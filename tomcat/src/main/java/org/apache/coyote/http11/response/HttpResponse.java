package org.apache.coyote.http11.response;


import org.apache.coyote.http11.HttpProtocol;

public class HttpResponse {

    private HttpProtocol httpProtocol;
    private HttpStatusCode httpStatusCode;
    private HttpResponseHeaders httpResponseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, HttpResponseHeaders httpResponseHeaders, ResponseBody responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatusCode = httpStatusCode;
        this.httpResponseHeaders = httpResponseHeaders;
        this.responseBody = responseBody;
    }

    public HttpResponse(HttpProtocol httpProtocol, HttpStatusCode httpStatusCode, HttpResponseHeaders httpResponseHeaders) {
        this(httpProtocol, httpStatusCode, httpResponseHeaders, ResponseBody.EMPTY);
    }

    public HttpResponse(HttpProtocol httpProtocol) {
        this.httpProtocol = httpProtocol;
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

    public void setResponse(HttpStatusCode httpStatusCode, HttpResponseHeaders httpResponseHeaders) {
        this.setResponse(httpStatusCode, httpResponseHeaders, new ResponseBody(new byte[0]));
    }

    public void setResponse(HttpStatusCode httpStatusCode, HttpResponseHeaders httpResponseHeaders, ResponseBody responseBody) {
        this.httpStatusCode = httpStatusCode;
        this.httpResponseHeaders = httpResponseHeaders;
        this.responseBody = responseBody;
    }

    private String firstLine() {
        return httpProtocol.description() + " " + httpStatusCode.getCode() + " " + httpStatusCode.getDescription();
    }


    private String contentInfo() {
        return responseBody.toContentLengthHeaderMessage();
    }


    private String content() {
        return responseBody.toString();
    }
}
