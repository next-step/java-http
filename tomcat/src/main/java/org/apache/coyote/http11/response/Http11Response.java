package org.apache.coyote.http11.response;

import java.util.Objects;
import java.util.stream.Stream;
import org.apache.coyote.http11.response.header.ContentLength;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.statusline.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Response extends HttpResponse {

    private Http11Response(final HttpResponseBuilder responseBuilder) {
        super(responseBuilder.statusLine, responseBuilder.messageBody,
                responseBuilder.http11ResponseHeader);
    }

    public static class HttpResponseBuilder {

        private static final Logger log = LoggerFactory.getLogger(HttpResponseBuilder.class);

        private StatusLine statusLine;
        private MessageBody messageBody;
        private Http11ResponseHeader http11ResponseHeader;

        public HttpResponseBuilder() {
        }

        public static Http11Response.HttpResponseBuilder builder() {
            return new Http11Response.HttpResponseBuilder();
        }

        public Http11Response.HttpResponseBuilder statusLine(String version, String status) {
            this.statusLine = new StatusLine(version, status);
            return this;
        }

        public Http11Response.HttpResponseBuilder responseHeader(String contentType,
                                                                 int responseBodySize) {
            this.http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                    .builder()
                    .contentType(contentType)
                    .contentLength(responseBodySize)
                    .build();
            return this;
        }

        public Http11Response.HttpResponseBuilder responseHeader(Http11ResponseHeader http11ResponseHeader) {
            this.http11ResponseHeader = http11ResponseHeader;
            return this;
        }

        public Http11Response.HttpResponseBuilder messageBody(Stream<String> content) {
            this.messageBody = new MessageBody(content);
            this.http11ResponseHeader.setContentLength(new ContentLength(this.messageBody.getContentLength()));
            return this;
        }

        public Http11Response build() {
            validate();

            return new Http11Response(this);
        }

        private void validate() {
            if (Objects.isNull(statusLine)) {
                log.error("Http11Response{" +
                        "statusLine=" + statusLine);
                throw new IllegalStateException("빌더: HttpResponse의 필드값이 할당되지 않았습니다.");
            }
        }
    }
}
