package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Path {

    private final String path;
    private Map<String, Object> queryParamMap = Map.of();

    public Path(String fullPath) {
        String[] values = fullPath.split("\\?");
        this.path = values[0];
        if (values.length > 1) {
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


    private Map<String, Object> parseQueryParam(String rawQueryParam) {
        return Arrays.stream(rawQueryParam.split("&"))
                .map(query -> {
                    String[] pair = parseKeyValue(query);
                    return Map.entry(pair[0], pair[1]);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static String[] parseKeyValue(String query) {
        return query.split("=");
    }
}
