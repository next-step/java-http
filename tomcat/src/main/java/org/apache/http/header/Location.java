package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpPath;

/**
 * https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Location
 */
public class Location extends HttpHeader {
    private static final HeaderName HEADER_NAME = HeaderName.LOCATION;

    private final HttpPath path;

    public Location(String path) {
        this.path = new HttpPath(path);
    }

    @Override
    public Pair<HeaderName, HttpHeader> getHeader() {
        return Pair.of(HEADER_NAME, this);
    }

    @Override
    public String toString() {
        return HEADER_NAME + DELIMITER + path;
    }

}
