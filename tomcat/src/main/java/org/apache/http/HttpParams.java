package org.apache.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpParams {
    public final Map<String, String> params = new HashMap<>();

    public HttpParams(final String url) {
        if (!url.contains("?")) {
            return;
        }
        parseQueryString(url.split("\\?")[1]);
    }

    public Optional<String> get(final String key) {
        return Optional.ofNullable(params.get(key));
    }

    private void parseQueryString(final String queryString) {
        final var values = queryString.split("&");
        for (String param : values) {
            final String key = param.split("=")[0];
            final String value = param.split("=")[1];
            params.put(key, value);
        }
    }
}
