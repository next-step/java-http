package org.apache.coyote.http11.request;

import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.http11.response.ContentType;

public class HttpPath {

    private static final String DELIMITER = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_INDEX = 1;
    private static final String DOT = ".";
    private static final String DEFAULT_EXTENSION = ContentType.HTML.getExtension();

    private final String path;
    private final QueryString query;

    private HttpPath(String path, QueryString query) {
        this.path = path;
        this.query = query;
    }

    private HttpPath(String path, String queryString) {
        this(path, QueryString.from(queryString));
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

    public QueryString getQuery() {
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

    private boolean hasExtension() {
        return path.contains(DOT);
    }
}
