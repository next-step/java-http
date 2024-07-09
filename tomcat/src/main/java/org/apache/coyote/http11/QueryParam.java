package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParam {

    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_PAIR_DELIMITER = "=";

    private Map<String, Object> queryParamMap = Map.of();

    public QueryParam(String value) {
        this.queryParamMap = parseQueryParam(value);
    }

    public Map<String, Object> value() {
        return queryParamMap;
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
