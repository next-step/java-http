package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Path {

    private static final String PATH_QUERY_PARAM_SEPARATOR = "\\?";
    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_PAIR_DELIMITER = "=";

    private final String path;
    private Map<String, Object> queryParamMap = Map.of();

    public Path(String fullPath) {
        String[] values = fullPath.split(PATH_QUERY_PARAM_SEPARATOR);
        this.path = values[0];
        if (hasQueryParam(values)) {
            this.queryParamMap = parseQueryParam(values[1]);
        }
    }

    public boolean endsWith(String value) {
        return path.endsWith(value);
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getQueryParamMap() {
        return queryParamMap;
    }


    private static boolean hasQueryParam(String[] values) {
        return values.length > 1;
    }


    private Map<String, Object> parseQueryParam(String rawQueryParam) {
        return Arrays.stream(rawQueryParam.split(QUERY_PARAM_DELIMITER))
                .map(query -> {
                    String[] pair = parseKeyValue(query);
                    return Map.entry(pair[0], pair[1]);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static String[] parseKeyValue(String query) {
        return query.split(KEY_VALUE_PAIR_DELIMITER);
    }
}
