package org.apache.coyote.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamsMapping {
    private static final String QUERY_SEPARATOR = "&";
    private static final String QUERY_ASSIGNMENT = "=";
    private static final int QUERY_KEY_VALUE_NUMBER = 2;
    private static final int QUERY_KEY_POINT = 0;
    private static final int QUERY_VALUE_POINT = 1;
    private static final String DEFAULT_QUERY_VALUE = "";

    private final Map<String, String> params = new HashMap<>();

    public void toQueryStringMapping(final String queryString) {
        final Map<String, String> queryStringMapping = Arrays.stream(queryString.split(QUERY_SEPARATOR))
                .map(param -> param.split(QUERY_ASSIGNMENT, 2))
                .collect(Collectors.toMap(ParamsMapping::queryKey, ParamsMapping::queryValue));

        this.params.putAll(queryStringMapping);
    }

    private static String queryKey(String[] pair) {
        return pair[QUERY_KEY_POINT];
    }

    private static String queryValue(String[] pair) {
        if (pair.length < QUERY_KEY_VALUE_NUMBER) {
            return DEFAULT_QUERY_VALUE;
        }
        return pair[QUERY_VALUE_POINT];
    }

    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(this.params);
    }

    public String getParam(final String parameterName) {
        return this.params.get(parameterName);
    }
}
