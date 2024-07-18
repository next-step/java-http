package org.apache.coyote.http11.request;

import java.util.Objects;
import org.apache.coyote.http11.exception.RequestMethodNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Request extends HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(Http11Request.class);

    private Http11Request(final HttpRequestBuilder requestBuilder) {
        super(requestBuilder.requestMethod, requestBuilder.requestUrl, requestBuilder.protocol,
            requestBuilder.protocol.getHttpVersion(), requestBuilder.requestUrl.getParams());
    }

    public static class HttpRequestBuilder {

        private RequestMethod requestMethod;
        private RequestUrl requestUrl;
        private RequestProtocol protocol;

        private HttpRequestBuilder() {
        }

        public static HttpRequestBuilder builder() {
            return new HttpRequestBuilder();
        }

        public HttpRequestBuilder requestMethod(String requestMethod) {
            try {
                this.requestMethod = RequestMethod.valueOf(requestMethod);
            } catch (IllegalArgumentException e) {
                throw new RequestMethodNotFoundException("지원하지 않는 HTTP Request Method 입니다.");
            }

            return this;
        }

        public HttpRequestBuilder requestUrl(String url) {
            this.requestUrl = new RequestUrl(url);
            return this;
        }

        public HttpRequestBuilder requestProtocol(String protocol) {
            this.protocol = new RequestProtocol(protocol);
            return this;
        }

        public Http11Request build() {
            validate();

            return new Http11Request(this);
        }

        private void validate() {
            if (Objects.isNull(requestMethod) || Objects.isNull(requestUrl) || Objects.isNull(
                protocol)) {
                log.error("Http11Request{" +
                    "requestMethod=" + requestMethod +
                    ", requestUrl=" + requestUrl +
                    ", protocol=" + protocol
                );
                throw new IllegalStateException("빌더: HttpRequest의 필드값이 할당되지 않았습니다.");
            }
        }
    }
}
