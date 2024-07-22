package org.apache.coyote.http11.response;

import java.util.Objects;

public class StatusLine {

    private final HttpResponseProtocol httpResponseProtocol;
    private final StatusCode statusCode;

    public StatusLine(String version, String statusCode) {
        validate(version, statusCode);
        this.statusCode = StatusCode.valueOf(statusCode);
        this.httpResponseProtocol = new HttpResponseProtocol(version);
    }

    private void validate(String version, String statusCode) {
        if (Objects.isNull(version) || version.isEmpty() || Objects.isNull(statusCode)
            || statusCode.isEmpty()) {
            throw new IllegalStateException("response Status의 값이 비어있습니다.");
        }
    }

    @Override
    public String toString() {
        return httpResponseProtocol + " " + statusCode + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StatusLine that = (StatusLine) o;
        return Objects.equals(httpResponseProtocol, that.httpResponseProtocol)
            && statusCode == that.statusCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpResponseProtocol, statusCode);
    }
}
