package org.apache.http.header;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length
 */
public class ContentLength extends HttpHeader {
    private static final String HEADER_NAME = "Content-Length";
    
    public final int length;

    public ContentLength(int length) {
        this.length = length;
    }

    @Override
    public String getHeaderName() {
        return HEADER_NAME;
    }

    @Override
    public String toString() {
        return HEADER_NAME + ": " + length + " ";
    }
}
