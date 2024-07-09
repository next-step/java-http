package org.apache.coyote.request;

import org.apache.exception.BadPathException;

public class HttpPath {
    private static final String PATH_DELIMITER = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;
    private static final int PATH_ONLY_LENGTH = 1;
    private static final int PATH_AND_QUERY_STRING_LENGTH = 2;

    private final String path;
    private final QueryString queryString;

    public HttpPath(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static HttpPath parse(String httpPath) {
        String[] pathParts = httpPath.split(PATH_DELIMITER);
        String path = pathParts[PATH_INDEX];
        if (containsPathOnly(pathParts)) {
            return new HttpPath(path, new QueryString());
        }
        if (containsPathAndQueryString(pathParts)) {
            return new HttpPath(path, QueryString.parse(pathParts[QUERY_STRING_INDEX]));
        }
        throw new BadPathException();
    }

    private static boolean containsPathOnly(String[] pathParts) {
        return pathParts.length == PATH_ONLY_LENGTH;
    }

    private static boolean containsPathAndQueryString(String[] pathParts) {
        return pathParts.length == PATH_AND_QUERY_STRING_LENGTH;
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    public String findQueryParam(String key) {
        return queryString.findQueryParam(key);
    }
}
