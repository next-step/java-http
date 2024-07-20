package org.apache.coyote.http11.request;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryString {

    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_PAIR_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    public static final QueryString EMPTY = new QueryString();

    private Map<String, Object> queryParamMap = Map.of();

    private QueryString() {
    }

    public QueryString(String value) {
        this.queryParamMap = parseQueryString(value);
    }

    public Map<String, Object> value() {
        return queryParamMap;
    }

    private Map<String, Object> parseQueryString(String rawQueryString) {
        return convertToMap(rawQueryString.split(QUERY_PARAM_DELIMITER));
    }

    private Map<String, Object> convertToMap(String[] params) {
        return Arrays.stream(params)
                .map(QueryString::parseKeyValue)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, Object> parseKeyValue(String query) {
        String[] pair = query.split(KEY_VALUE_PAIR_DELIMITER);
        return Map.entry(pair[KEY_INDEX], pair[VALUE_INDEX]);
    }

    @Override
    public final boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof QueryString that)) return false;

        return Objects.equals(queryParamMap, that.queryParamMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(queryParamMap);
    }
}
