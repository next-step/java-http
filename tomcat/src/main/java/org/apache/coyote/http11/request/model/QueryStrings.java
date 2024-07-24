package org.apache.coyote.http11.request.model;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

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

    public boolean isEmpty() {
        return queryString.isEmpty();
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryStrings that = (QueryStrings) o;
        return Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(queryString);
    }
}
