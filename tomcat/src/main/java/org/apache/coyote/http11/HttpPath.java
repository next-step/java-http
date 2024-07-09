package org.apache.coyote.http11;

import org.apache.commons.lang3.StringUtils;

public class HttpPath {

    private static final String DELIMITER = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private static final String DOT = ".";
    private static final String ROOT_PATH = "/";
    private static final String DEFAULT_EXTENSION = ContentType.HTML.getExtension();

    private final String path;
    private final Query query;

    private HttpPath(String path, Query query) {
        this.path = path;
        this.query = query;
    }

    private HttpPath(String path, String queryString) {
        this(path, Query.from(queryString));
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

    public String getExtension() {
        int index = path.lastIndexOf(DOT);
        if (!hasExtension()) {
            return StringUtils.EMPTY;
        }
        return path.substring(index);
    }

    public String getFilePath() {
        if (!hasExtension()) {
            return path + DEFAULT_EXTENSION;
        }
        return path;
    }

    public boolean isRootPath() {
        return path.equals(ROOT_PATH);
    }

    private boolean hasExtension() {
        return path.contains(DOT);
    }
}
