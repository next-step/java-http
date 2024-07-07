package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpVersionException;

public class HttpVersion {
    private final String protocol;
    private final String version;

    public HttpVersion(final String httpVersion) {
        final String[] split = parseHttpVersion(httpVersion);
        this.protocol = split[0];
        this.version = split[1];
    }

    private static String[] parseHttpVersion(final String httpVersion) {
        final String[] split = httpVersion.split("/");

        if (split.length != 2) {
            throw new InvalidHttpVersionException("Invalid HTTP version: " + httpVersion);
        }

        return split;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
