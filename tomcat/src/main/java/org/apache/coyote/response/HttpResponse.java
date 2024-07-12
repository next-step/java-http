package org.apache.coyote.response;

public class HttpResponse {
    private final HttpStatus status;
    private final MimeType mimeType;
    private final String responseBody;

    public HttpResponse(HttpStatus status, MimeType mimeType, String responseBody) {
        this.status = status;
        this.mimeType = mimeType;
        this.responseBody = responseBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String buildContent() {
        return String.join("\r\n",
                "HTTP/1.1 " + status + " ",
                "Content-Type: " + mimeType.getContentType() + " ",
                "Content-Length: " + responseBody.getBytes().length + " ",
                "",
                responseBody);    }
}
