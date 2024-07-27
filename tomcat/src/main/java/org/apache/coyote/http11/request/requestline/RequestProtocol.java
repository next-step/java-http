package org.apache.coyote.http11.request.requestline;

import org.apache.coyote.http11.exception.RequestProtocolInvalidException;

import java.util.Objects;
import java.util.regex.Pattern;

public class RequestProtocol {

    public static final int LENGTH = 2;
    private static final int PROTOCOL_NAME = 0;
    private static final int PROTOCOL_VERSION = 1;
    private static final Pattern DELIMITER = Pattern.compile("/");
    public final HttpVersion httpVersion;
    public final String protocol;

    public RequestProtocol(final String protocol) {
        validate(protocol);
        String[] protocols = DELIMITER.split(protocol);
        this.httpVersion = new HttpVersion(protocols[PROTOCOL_VERSION]);
        this.protocol = protocols[PROTOCOL_NAME];
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public String getProtocol() {
        return protocol;
    }

    private void validate(final String protocol) {
        if (Objects.isNull(protocol) || protocol.isEmpty()) {
            throw new RequestProtocolInvalidException("Request Protocol의 값이 확인이 필요합니다.");
        }

        if (DELIMITER.split(protocol).length != LENGTH) {
            throw new RequestProtocolInvalidException("Request Protocol의 값이 확인이 필요합니다.");
        }

    }

    @Override
    public String toString() {
        return "RequestProtocol{" +
                "httpVersion=" + httpVersion +
                ", protocol='" + protocol + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestProtocol that = (RequestProtocol) o;
        return Objects.equals(httpVersion, that.httpVersion) && Objects.equals(
                protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpVersion, protocol);
    }
}
