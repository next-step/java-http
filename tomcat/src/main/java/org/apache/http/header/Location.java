package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpPath;

/**
 * https://developer.mozilla.org/ko/docs/Web/HTTP/Headers/Location
 */
public class Location implements HttpResponseHeader {
    private final HttpPath path;

    public Location(String path) {
        this.path = new HttpPath(path);
    }

    public Location(HttpPath path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Location" + DELIMITER + path;
    }

}
