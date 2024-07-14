package org.apache.coyote.http;

import java.util.Arrays;

public enum HttpVersion {
    NONE(""),
    HTTP0_9("HTTP/0.9"),
    HTTP1("HTTP/1"),
    HTTP1_1("HTTP/1.1"),
    HTTP2("HTTP/2"),
    HTTP3("HTTP/3");

    private final String version;

    HttpVersion(final String version) {
        this.version = version;
    }

    public static HttpVersion from(final String responseLineMetaDatum) {
        return Arrays.stream(values()).filter(httpVersion -> httpVersion.version.equals(responseLineMetaDatum)).findFirst().orElse(NONE);
    }

    public String getVersion() {
        return version;
    }
}
