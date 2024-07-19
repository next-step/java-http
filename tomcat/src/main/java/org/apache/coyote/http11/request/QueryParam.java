package org.apache.coyote.http11.request;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryParam {

    private static final String QUERY_PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_PAIR_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    public static final QueryParam EMPTY = new QueryParam();

    private Map<String, Object> queryParamMap = Map.of();

    private QueryParam() {
    }

    public QueryParam(String value) {
        this.queryParamMap = parseQueryParam(value);
    }

    public Map<String, Object> value() {
        return queryParamMap;
    }

    private Map<String, Object> parseQueryParam(String rawQueryParam) {
        return convertToMap(rawQueryParam.split(QUERY_PARAM_DELIMITER));
    }

    private Map<String, Object> convertToMap(String[] params) {
        return Arrays.stream(params)
                .map(QueryParam::parseKeyValue)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static Map.Entry<String, Object> parseKeyValue(String query) {
        String[] pair = query.split(KEY_VALUE_PAIR_DELIMITER);
        return Map.entry(pair[KEY_INDEX], pair[VALUE_INDEX]);
    }
}
