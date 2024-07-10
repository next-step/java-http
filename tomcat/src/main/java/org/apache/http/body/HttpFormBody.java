package org.apache.http.body;

import org.apache.http.HttpParams;
import org.apache.http.header.HttpHeaders;

public class HttpFormBody extends HttpBody {
    private final HttpParams params;

    public HttpFormBody(final String body) {
        params = new HttpParams(body);
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
        return params.toString();
    }
}
