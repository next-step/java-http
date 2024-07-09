package org.apache.http.header;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type
 */
public class ContentType extends HttpHeader {
    private static final String HEADER_NAME = "Content-Type";
    private static final String DEFAULT_CHARSET = "charset=utf-8";

    private final MediaType mediaType;

    public ContentType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String getHeaderName() {
        return HEADER_NAME;
    }

    @Override
    public String toString() {
        return HEADER_NAME + ": " + mediaType + ";" + DEFAULT_CHARSET + " ";
    }
}
