package org.apache.coyote.http11.response;

import java.util.Objects;
import org.apache.coyote.http11.exception.RequestProtocolInvalidException;

public class HttpResponseProtocol {

    private static final String HTTP = "HTTP";

    public final HttpResponseVersion httpVersion;
    public final String protocol;

    public HttpResponseProtocol(final String version) {
        validate(version);

        this.httpVersion = new HttpResponseVersion(version);
        this.protocol = HTTP;
    }

    public HttpResponseVersion getHttpVersion() {
        return httpVersion;
    }

    public String getProtocol() {
        return protocol;
    }

    private void validate(final String version) {
        if (Objects.isNull(version) || version.isEmpty()) {
            throw new RequestProtocolInvalidException("Response Version의 값이 확인이 필요합니다.");
        }
    }

    @Override
    public String toString() {
        return protocol + "/" + httpVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpResponseProtocol that = (HttpResponseProtocol) o;
        return Objects.equals(httpVersion, that.httpVersion) && Objects.equals(
            protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpVersion, protocol);
    }
}
