package org.apache.http.body;

import org.apache.http.header.HttpHeaders;

public abstract class HttpBody {
    abstract public HttpHeaders addContentHeader(HttpHeaders headers);

    @Override
    abstract public String toString();
}
