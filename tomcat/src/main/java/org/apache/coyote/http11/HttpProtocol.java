package org.apache.coyote.http11;

import java.util.Set;

public class HttpProtocol {
    private static final Set<String> SUPPORT_VERSIONS = Set.of("HTTP/1.0", "HTTP/1.1");

    private final String version;

    private HttpProtocol(final String version) {
        this.version = version;
    }

    public static HttpProtocol from(final String version) {
        validate(version);
        return new HttpProtocol(version);
    }

    private static void validate(final String version) {
        if (!SUPPORT_VERSIONS.contains(version)) {
            throw new IllegalArgumentException("HTTP가 아니거나 지원하지 않는 HTTP 버전 입니다.");
        }
    }

    public String getVersion() {
        return version;
    }
}
