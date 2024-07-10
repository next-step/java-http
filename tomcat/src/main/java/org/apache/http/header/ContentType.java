package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.file.MediaType;

/**
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Type
 */
public class ContentType extends HttpRequestHeader implements HttpResponseHeader {
    private static final RequestHeaderName HEADER_NAME = RequestHeaderName.CONTENT_TYPE;
    private static final String DEFAULT_CHARSET = ";charset=utf-8";

    private final MediaType mediaType;

    public ContentType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public ContentType(String mediaType) {
        this.mediaType = new MediaType(mediaType.replace(DEFAULT_CHARSET, ""));
    }

    public boolean match(final MediaType type) {
        return mediaType.equals(type);
    }

    @Override
    public Pair<RequestHeaderName, HttpRequestHeader> getHeader() {
        return Pair.of(HEADER_NAME, this);
    }

    @Override
    public String toString() {
        return HEADER_NAME + HttpResponseHeader.DELIMITER + mediaType + DEFAULT_CHARSET + " ";
    }
}
