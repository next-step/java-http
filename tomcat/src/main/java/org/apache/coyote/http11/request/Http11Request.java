package org.apache.coyote.http11.request;

import org.apache.coyote.http11.exception.RequestMethodNotFoundException;
import org.apache.coyote.http11.request.header.Cookie;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.request.requestline.RequestProtocol;
import org.apache.coyote.http11.request.requestline.RequestUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Http11Request extends HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(Http11Request.class);

    private Http11Request(final HttpRequestBuilder requestBuilder) {
        super(requestBuilder.requestMethod, requestBuilder.requestUrl, requestBuilder.protocol,
                requestBuilder.protocol.getHttpVersion(), requestBuilder.requestUrl.getParams(),
                requestBuilder.requestBody, requestBuilder.cookie);
    }

    public static class HttpRequestBuilder {

        private static final Pattern DELIMITER = Pattern.compile("=");
        private final static int KEY_INDEX = 0;
        private final static int VALUE_INDEX = 1;

        private RequestMethod requestMethod;
        private RequestUrl requestUrl;
        private RequestProtocol protocol;
        private RequestBody requestBody;
        private Cookie cookie;

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

        public HttpRequestBuilder cookie(String cookie) {

            Map<String, String> cookies = Arrays.stream(cookie
                            .split(";"))
                    .map(DELIMITER::split)
                    .collect(Collectors.toMap(words -> words[KEY_INDEX].trim(),
                            words -> words[VALUE_INDEX].trim()));

            this.cookie = new Cookie(cookies);

            return this;
        }

        public HttpRequestBuilder requestBody(String requestBody) {
            this.requestBody = new RequestBody(requestBody);

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
