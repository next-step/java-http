package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Length
 */
public class ContentLength extends HttpRequestHeader implements HttpResponseHeader{
    private static final RequestHeaderParser parser = RequestHeaderParser.CONTENT_LENGTH;

    protected final int length;

    public ContentLength(int length) {
        this.length = length;
    }

    public ContentLength() {
        this.length = -1;
    }

    public ContentLength(String length) {
        this.length = Integer.parseInt(length);
    }

    @Override
    RequestHeaderParser getParser() {
        return parser;
    }

    @Override
    public String toString() {
        return parser + HttpResponseHeader.DELIMITER + length + " ";
    }
}
