package org.apache.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpParams {
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private Map<String, String> params = new HashMap<>();

    HttpParams(Map<String, String> params) {
        this.params = params;
    }

    public HttpParams(final String url) {
        parseQueryString(url);
    }

    public String get(final String key) {
        return params.get(key);
    }

    private void parseQueryString(final String queryString) {
        final var values = queryString.split(PARAM_DELIMITER);
        for (String param : values) {
            var token = param.split(KEY_VALUE_DELIMITER);
            if (token.length < 2) continue;
            final String key = token[0];
            final String value = token[1];
            params.put(key, value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpParams that = (HttpParams) o;
        return Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }

    @Override
    public String toString() {
        return params.entrySet().stream()
                .map(param -> param.getKey() + KEY_VALUE_DELIMITER + param.getValue())
                .collect(Collectors.joining(PARAM_DELIMITER));
    }
}
