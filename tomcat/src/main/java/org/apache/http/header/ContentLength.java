package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length
 */
public class ContentLength extends HttpRequestHeader implements HttpResponseHeader{
    private static final RequestHeaderName HEADER_NAME = RequestHeaderName.CONTENT_LENGTH;

    protected final int length;

    public ContentLength(int length) {
        this.length = length;
    }

    public ContentLength(String length) {
        this.length = Integer.parseInt(length);
    }

    @Override
    Pair<RequestHeaderName, HttpRequestHeader> getHeader() {
        return Pair.of(HEADER_NAME, this);
    }

    @Override
    public String toString() {
        return HEADER_NAME + HttpResponseHeader.DELIMITER + length + " ";
    }
}
