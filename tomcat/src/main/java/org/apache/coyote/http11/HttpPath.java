package org.apache.coyote.http11;

import org.apache.commons.lang3.StringUtils;

public class HttpPath {

    private static final String DELIMITER = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;

    private final String path;
    private final Query query;

    private HttpPath(String path, String queryString) {
        this.path = path;
        this.query = Query.from(queryString);
    }

    public static HttpPath from(String httpPath) {
        String[] tokens = httpPath.split(DELIMITER);
        String path = tokens[PATH_INDEX];
        if (tokens.length == 1) {
            return new HttpPath(path, StringUtils.EMPTY);
        }
        return new HttpPath(path, tokens[QUERY_INDEX]);
    }

    public String getPath() {
        return path;
    }

    public Query getQuery() {
        return query;
    }
}
