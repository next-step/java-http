package org.apache.coyote.http11.model;

public class HttpProtocol {
    private final String protocol;

    public HttpProtocol(final String protocol) {
        this.protocol = protocol;
    }

    public String protocol() {
        return protocol;
    }
}
