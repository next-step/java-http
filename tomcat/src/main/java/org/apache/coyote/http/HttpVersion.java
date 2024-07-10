package org.apache.coyote.http;

public enum HttpVersion {
    HTTP0_9("HTTP/0.9"),
    HTTP1("HTTP/1"),
    HTTP1_1("HTTP/1.1"),
    HTTP2("HTTP/2"),
    HTTP3("HTTP/3");

    private final String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
