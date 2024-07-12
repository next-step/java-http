package org.apache.coyote.http;

import java.util.Arrays;

public enum HttpHeader {
    HOST("Host"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    COOKIE("Cookie"),
    ;

    private final String header;

    HttpHeader(final String header) {
        this.header = header;
    }

    public static HttpHeader from(final String name) {
        return Arrays.stream(values()).filter(header -> header.header.equals(name)).findFirst().orElse(null);
    }

    public String header() {
        return this.header;
    }
}
