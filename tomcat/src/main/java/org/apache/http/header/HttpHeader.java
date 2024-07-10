package org.apache.http.header;

public abstract class HttpHeader {
    abstract String getHeaderName();

    @Override
    abstract public String toString();
}
