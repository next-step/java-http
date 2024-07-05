package org.apache.coyote.http11.model;

import java.util.HashMap;

public class Path {
    private final String path;
    private final QueryParams queryParams;

    public Path(final String path, final QueryParams queryParams) {
        this.path = path;
        this.queryParams = queryParams;
    }

    public static Path of(final String path, final HashMap<String, String> queryParams) {
        return new Path(path, new QueryParams(queryParams));
    }

    public String path() {
        return path;
    }

    public QueryParams queryParams() {
        return queryParams;
    }
}
