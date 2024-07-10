package org.apache.http.body;

import org.apache.http.header.HttpHeaders;

public abstract class HttpBody {
    abstract public HttpHeaders addContentHeader(HttpHeaders headers);

    public String getValue(String key) {
        return null;
    }

    @Override
    abstract public String toString();
}
