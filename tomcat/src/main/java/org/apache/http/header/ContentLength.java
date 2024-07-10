package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length
 */
public class ContentLength extends HttpHeader {
    private static final HeaderName HEADER_NAME = HeaderName.CONTENT_LENGTH;

    private final int length;

    public ContentLength(int length) {
        this.length = length;
    }

    public ContentLength(String length) {
        this.length = Integer.parseInt(length);
    }

    @Override
    Pair<HeaderName, HttpHeader> getHeader() {
        return Pair.of(HEADER_NAME, this);
    }

    @Override
    public String toString() {
        return HEADER_NAME + DELIMITER + length + " ";
    }
}
