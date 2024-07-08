package org.apache.coyote.http11.model;

public class HttpVersion {
    private final String version;

    public HttpVersion(final String version) {
        this.version = version;
    }

    public String version() {
        return version;
    }
}
