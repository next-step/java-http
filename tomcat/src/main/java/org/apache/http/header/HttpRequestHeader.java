package org.apache.http.header;

public abstract class HttpRequestHeader {
    public static final String DELIMITER = ": ";

    abstract RequestHeaderParser getParser();

    @Override
    abstract public String toString();
}
