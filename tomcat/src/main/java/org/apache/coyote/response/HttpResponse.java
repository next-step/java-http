package org.apache.coyote.response;

import java.util.Optional;

public class HttpResponse {
    private final HttpStatus status;
    private final MimeType mimeType;
    private final ResponseHeaders responseHeaders;
    private final String responseBody;

    public HttpResponse(HttpStatus status, MimeType mimeType, String responseBody) {
        this(status, mimeType, new ResponseHeaders(), responseBody);
    }

    public HttpResponse(HttpStatus status, MimeType mimeType, ResponseHeaders responseHeaders, String responseBody) {
        this.status = status;
        this.mimeType = mimeType;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse notFound() {
        return new HttpResponse(HttpStatus.NOT_FOUND,
                MimeType.HTML,
                FileFinder.find("/404.html"));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void addCookie(String sessionId) {
        responseHeaders.add("Set-Cookie", sessionId);
    }

    public String buildContent() {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("HTTP/1.1 ").append(status).append(" ").append("\r\n");
        contentBuilder.append("Content-Type: ").append(mimeType.getContentType()).append(" ").append("\r\n");
        contentBuilder.append("Content-Length: ").append(responseBody.getBytes().length).append(" ").append("\r\n");
        Optional<String> cookie = Optional.ofNullable(responseHeaders.get("Set-Cookie"));
        cookie.ifPresent(it -> contentBuilder.append("Set-Cookie: ").append("JSESSIONID=").append(it).append(" ").append("\r\n"));
        contentBuilder.append("\r\n");

        contentBuilder.append(responseBody);
        return contentBuilder.toString();
    }
}
