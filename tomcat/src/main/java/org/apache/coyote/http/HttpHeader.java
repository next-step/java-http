package org.apache.coyote.http;

public enum HttpHeader {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    ;

    private final String header;

    HttpHeader(final String header) {
        this.header = header;
    }

    public String header() {
        return this.header;
    }
}
