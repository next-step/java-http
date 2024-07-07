package org.apache.coyote.http11;

import java.util.Objects;

public class RequestLine {

    private static final String DELIMITER = " ";
    private final String method;
    private final String path;
    private final String protocol;
    private final String version;

    private RequestLine(String method, String path, String protocol, String version) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine from(String requestLine) {
        String[] tokens = requestLine.split(DELIMITER);
        String method = tokens[0];
        String path = tokens[1];
        String[] protocolAndVersion = tokens[2].split("/");
        String protocol = protocolAndVersion[0];
        String version = protocolAndVersion[1];
        return new RequestLine(method, path, protocol, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(path, that.path) && Objects.equals(protocol,
                                                                                                        that.protocol)
               && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol, version);
    }
}
