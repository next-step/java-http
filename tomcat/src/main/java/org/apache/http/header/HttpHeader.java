package org.apache.http.header;

import org.apache.commons.lang3.tuple.Pair;

public abstract class HttpHeader {
    public static final String DELIMITER = ": ";

    abstract Pair<HeaderName, HttpHeader> getHeader();

    @Override
    abstract public String toString();
}
