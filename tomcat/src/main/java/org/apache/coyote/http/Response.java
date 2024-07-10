package org.apache.coyote.http;

public class Response {

    private final ResponseLine responseLine = new ResponseLine();
    private final HeaderMapping headerMapping = new HeaderMapping();
    private final StringBuilder body = new StringBuilder();

    public void init() {
        this.responseLine.setHttpVersion(HttpVersion.HTTP1_1);
        this.responseLine.setStatusCode(StatusCode.OK);
        this.headerMapping.addHeader(HttpHeader.CONTENT_TYPE, ContentType.TEXT_PLAIN.type() + ContentType.CHARSET_UTF_8.type());
    }

    public void setResponseLine(final HttpVersion httpVersion, final StatusCode statusCode) {
        this.responseLine.setHttpVersion(httpVersion);
        this.responseLine.setStatusCode(statusCode);
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
        return String.join(Constants.CR.getValue() + Constants.LF.getValue(),
                responseLine.toResponseLine() + HeaderMapping.HEADER_SPACE,
                headerMapping.convertHttpHeaders(),
                "",
                body.toString()).getBytes();
    }
}
