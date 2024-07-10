package org.apache.http.body;

import org.apache.http.header.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class HttpFormBody extends HttpBody {
    private final Map<String, String> params = new HashMap<>();

    public HttpFormBody(final String body) {
        parseQueryString(body);
    }

    private void parseQueryString(final String queryString) {
        final var values = queryString.split("&");
        for (String param : values) {
            var token = param.split("=");
            if (token.length < 2) continue;
            final String key = token[0];
            final String value = token[1];
            params.put(key, value);
        }
    }

    @Override
    public HttpHeaders addContentHeader(HttpHeaders headers) {
        return null;
    }

    @Override
    public String getValue(String key) {
        return params.get(key);
    }

    @Override
    public String toString() {
        return null;
    }
}
