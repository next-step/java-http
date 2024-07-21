package org.apache.coyote.http11.request;

import java.util.Objects;

public class HttpVersion {

    private final String version;

    public HttpVersion(final String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "HttpVersion{" +
            "version='" + version + '\'' +
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
        HttpVersion that = (HttpVersion) o;
        return Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(version);
    }
}
