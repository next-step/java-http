package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpVersionException;

import java.util.Objects;

public class HttpVersion {
    public static final HttpVersion HTTP_11 = new HttpVersion("HTTP/1.1");

    private static final int REQUIRED_HTTP_VERSION_LENGTH = 2;
    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;
    private static final String DELIMITER = "/";

    private final String protocol;
    private final String version;

    public HttpVersion(final String httpVersion) {
        final String[] split = parseHttpVersion(httpVersion);
        this.protocol = split[PROTOCOL_INDEX];
        this.version = split[VERSION_INDEX];
    }

    private static String[] parseHttpVersion(final String httpVersion) {
        final String[] split = httpVersion.split(DELIMITER);

        if (split.length != REQUIRED_HTTP_VERSION_LENGTH) {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpVersion that = (HttpVersion) o;
        return Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
