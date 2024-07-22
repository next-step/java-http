package org.apache.coyote.http11.response;

import java.util.Objects;

public class Http11ResponseHeader {

    private final ContentType contentType;
    private final int contentLength;

    public Http11ResponseHeader(String contentType, int contentLength) {
        this.contentType = ContentType.valueOf(contentType);
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {

        return String.join("\r\n"
            , String.format(
                "Content-Type: %s ", contentType),
            String.format("Content-Length: %s ", contentLength)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Http11ResponseHeader that = (Http11ResponseHeader) o;
        return contentLength == that.contentLength && Objects.equals(contentType,
            that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType, contentLength);
    }
}


