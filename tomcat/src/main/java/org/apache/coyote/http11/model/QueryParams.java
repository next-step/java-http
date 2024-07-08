package org.apache.coyote.http11.model;

import java.util.Collections;
import java.util.Map;

public class QueryParams {
    private final Map<String, String> queryParams;

    public QueryParams(final Map<String, String> queryParams) {
        this.queryParams = Collections.unmodifiableMap(queryParams);
    }

    public String valueBy(final String key) {
        return queryParams.get(key);
    }

    public boolean isEmpty() {
        return queryParams.isEmpty();
    }
}
