package org.apache.coyote.http11.response;

import java.util.Objects;

public class HttpResponseVersion {

    private final String version;

    public HttpResponseVersion(final String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpResponseVersion that = (HttpResponseVersion) o;
        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(version);
    }
}
