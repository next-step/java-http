package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;

public abstract class HttpRequestHeader {
    public static final String DELIMITER = ": ";

    abstract Pair<RequestHeaderName, HttpRequestHeader> getHeader();

    @Override
    abstract public String toString();
}
