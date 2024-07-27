package org.apache.coyote.http11.response.header;

import java.util.Objects;

public class ContentLength {
    private final int contentLength;

    public ContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public int getContentLength() {
        return contentLength;
    }

    @Override
    public String toString() {
        return String.format("Content-Length: %s ", contentLength);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContentLength that = (ContentLength) o;
        return contentLength == that.contentLength;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contentLength);
    }
}
