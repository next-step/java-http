package org.apache.coyote.http11.response.header;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Http11ResponseHeader {

    private final ContentType contentType;
    private ContentLength contentLength;
    private Location location;
    private SetCookie setCookie;

    public Http11ResponseHeader(String contentType, int contentLength) {
        this.contentType = ContentType.valueOf(contentType);
        this.contentLength = new ContentLength(contentLength);
    }

    public Http11ResponseHeader(final HttpResponseHeaderBuilder builder) {
        this.contentType = builder.contentType;
        this.contentLength = builder.contentLength;
        this.location = builder.location;
        this.setCookie = builder.setCookie;
    }

    public void setContentLength(ContentLength contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        return Stream.of(contentType, contentLength, location, setCookie)
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .collect(Collectors.joining("\r\n"));

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

    public static class HttpResponseHeaderBuilder {

        private static final Logger log = LoggerFactory.getLogger(
                HttpResponseHeaderBuilder.class);

        private ContentType contentType;
        private ContentLength contentLength;
        private Location location;
        private SetCookie setCookie;

        public HttpResponseHeaderBuilder() {
        }

        public static Http11ResponseHeader.HttpResponseHeaderBuilder builder() {
            return new Http11ResponseHeader.HttpResponseHeaderBuilder();
        }

        public Http11ResponseHeader.HttpResponseHeaderBuilder contentType(String filename) {
            final ContentType extension =
                Arrays.stream(ContentType.values())
                    .filter(ext -> filename.endsWith(ext.name()))
                    .findFirst().orElseGet(() -> ContentType.all);
            this.contentType = extension;
            return this;
        }

        public Http11ResponseHeader.HttpResponseHeaderBuilder contentLength(int contentLength) {

            this.contentLength = new ContentLength(contentLength);
            return this;
        }

        public Http11ResponseHeader.HttpResponseHeaderBuilder location(String location) {

            this.location = new Location(location);
            return this;
        }

        public Http11ResponseHeader.HttpResponseHeaderBuilder cookie(Map<String, String> setCookie) {
            if (setCookie.containsKey("JSESSIONID")) {
                this.setCookie = new SetCookie(setCookie);
                return this;
            }

            return this;
        }

        public Http11ResponseHeader build() {
            validate();

            return new Http11ResponseHeader(this);
        }

        private void validate() {
            if (Objects.isNull(contentType)) {
                log.error("Http11ResponseHeader{" +
                        "contentType=" + contentType);
                throw new IllegalStateException("빌더: HttpResponseHeader의 필드값이 할당되지 않았습니다.");
            }
        }
    }
}


