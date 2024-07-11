package org.apache.coyote.http11;

public enum HttpHeaderName {
    CONTENT_TYPE("Content-Type"), COOKIE("Cookie"), CONTENT_LENGTH("Content-Length");

    private final String value;

    HttpHeaderName(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
