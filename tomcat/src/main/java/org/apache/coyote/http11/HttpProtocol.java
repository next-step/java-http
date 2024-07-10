package org.apache.coyote.http11;

import java.util.Arrays;

public enum HttpProtocol {

    HTTP11("HTTP", "1.1")
    ;

    private static final String DELIMITER = "/";
    private final String protocol;
    private final String version;

    HttpProtocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public static HttpProtocol from(String description) {
        return Arrays.stream(HttpProtocol.values())
                .filter(it -> it.description().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("HttpProtocol is invalid: " + description));
    }

    public String description() {
        return protocol + DELIMITER + version;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}