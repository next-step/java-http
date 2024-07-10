package org.apache.coyote.http11.request;

import java.util.Collections;
import java.util.Map;

public class QueryStrings {
    private final Map<String, String> queryString;

    public QueryStrings(Map<String, String> queryString) {
        this.queryString = Collections.unmodifiableMap(queryString);
    }

    public static QueryStrings emptyQueryStrings() {
        return new QueryStrings(Collections.emptyMap());
    }

    public String getQueryStringValueByKey(String key) {
        return queryString.get(key);
    }
}
