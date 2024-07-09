package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Path {

    private static final String PATH_QUERY_PARAM_SEPARATOR = "\\?";


    private final String path;
    private QueryParam queryParam;

    public Path(String fullPath) {
        String[] values = fullPath.split(PATH_QUERY_PARAM_SEPARATOR);
        this.path = values[0];
        if (hasQueryParam(values)) {
            this.queryParam = new QueryParam(values[1]);
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
