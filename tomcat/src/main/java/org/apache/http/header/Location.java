package org.apache.http.header;

import org.apache.http.HttpPath;

/**
 * https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Location
 */
public class Location extends HttpHeader {
    private static final String HEADER_NAME = "Location";

    private final HttpPath path;

    public Location(String path) {
        this.path = new HttpPath(path);
    }

    @Override
    public String getHeaderName() {
        return HEADER_NAME;
    }

    @Override
    public String toString() {
        return HEADER_NAME + ": " + path;
    }

}
