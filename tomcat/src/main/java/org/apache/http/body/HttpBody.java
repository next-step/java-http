package org.apache.http.body;

import org.apache.http.header.HttpResponseHeaders;

public abstract class HttpBody {
    abstract public HttpResponseHeaders addContentHeader(HttpResponseHeaders headers);

    public String getValue(String key) {
        return null;
    }

    @Override
    abstract public String toString();
}
