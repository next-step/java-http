package org.apache.coyote.http11;

import java.util.Map;

public class Path {

    private static final String PATH_QUERY_PARAM_SEPARATOR = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_PARAM_INDEX = 1;

    private final String path;
    private QueryParam queryParam = QueryParam.EMPTY;

    public Path(String fullPath) {
        String[] values = fullPath.split(PATH_QUERY_PARAM_SEPARATOR);
        this.path = values[PATH_INDEX];
        if (hasQueryParam(values)) {
            this.queryParam = new QueryParam(values[QUERY_PARAM_INDEX]);
        }
    }

    public boolean endsWith(String value) {
        return path.endsWith(value);
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getQueryParamMap() {
        return queryParam.value();
    }

    private static boolean hasQueryParam(String[] values) {
        return values.length > 1;
    }

}
