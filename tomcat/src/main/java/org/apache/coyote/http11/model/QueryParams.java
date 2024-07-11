package org.apache.coyote.http11.model;

import java.util.Collections;
import java.util.Map;

public class QueryParams implements HttpHeaderLine {
    private static final String QUERY_PARAM_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String EMPTY = "";

    private final Map<String, String> queryParams;

    public QueryParams(final Map<String, String> queryParams) {
        this.queryParams = Collections.unmodifiableMap(queryParams);
    }

    public static QueryParams emtpy() {
        return new QueryParams(Collections.emptyMap());
    }

    public String valueBy(final String key) {
        return queryParams.get(key);
    }

    public boolean isEmpty() {
        return queryParams.isEmpty();
    }

    @Override
    public String toLine() {
        return queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + KEY_VALUE_SEPARATOR + entry.getValue())
                .reduce((a, b) -> a + QUERY_PARAM_SEPARATOR + b)
                .orElse(EMPTY);
    }
}
