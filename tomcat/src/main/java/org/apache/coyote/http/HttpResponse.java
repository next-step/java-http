package org.apache.coyote.http;

public class HttpResponse {

    private final HttpResponseLine httpResponseLine = new HttpResponseLine();
    private final HeaderMapping headerMapping = new HeaderMapping();
    private final StringBuilder body = new StringBuilder();

    public void init() {
        this.httpResponseLine.setHttpVersion(HttpVersion.HTTP1_1);
        this.httpResponseLine.setStatusCode(StatusCode.OK);
        this.headerMapping.addHeader(HttpHeader.CONTENT_TYPE, ContentType.TEXT_PLAIN.type() + ContentType.CHARSET_UTF_8.type());
    }

    public void setResponseLine(final HttpVersion httpVersion, final StatusCode statusCode) {
        this.httpResponseLine.setHttpVersion(httpVersion);
        this.httpResponseLine.setStatusCode(statusCode);
    }

    public void setBody(final String body) {
        setBody(body, ContentType.TEXT_PLAIN);
    }

    public void setBody(final String body, final ContentType mimeType) {
        this.body.setLength(0);
        this.body.append(body);
        this.headerMapping.addHeader(HttpHeader.CONTENT_TYPE, ContentType.toContentTypeValues(mimeType, ContentType.CHARSET_UTF_8));
        this.headerMapping.addHeader(HttpHeader.CONTENT_LENGTH, Integer.toString(this.body.length()));
    }

    public byte[] toBytes() {
        return String.join(System.lineSeparator(),
                httpResponseLine.toResponseLine() + HeaderMapping.HEADER_SPACE,
                headerMapping.convertHttpHeaders(),
                "",
                body.toString()).getBytes();
    }
}
