package org.apache.coyote.http11;

public class HttpProtocol {
    private static final String SUPPORT_VERSION = "HTTP/1.1";

    private final String version;

    private HttpProtocol(final String version) {
        this.version = version;
    }

    public static HttpProtocol from(final String version) {
        String trimVersion = version.trim();
        validate(trimVersion);
        return new HttpProtocol(trimVersion);
    }

    private static void validate(final String version) {
        if (!SUPPORT_VERSION.equals(version)) {
            throw new InvalidHttpProtocolException();
        }
    }

    public String getVersion() {
        return version;
    }
}
