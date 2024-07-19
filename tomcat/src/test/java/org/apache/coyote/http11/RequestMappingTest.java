package org.apache.coyote.http11;

import org.apache.coyote.http11.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMappingTest {

    @Test
    @DisplayName("Request Line의 path가 `css/styles.css`일 경우 StaticResourceRequestHandler를 반환한다")
    public void getHandlerTest() {

        final var requestLine = new RequestLine("GET /css/styles.css HTTP/1.1");

        final var requestMapping = new RequestMapping();
        final var handler = requestMapping.getHandler(requestLine);

        assertThat(handler).isInstanceOf(StaticResourceRequestHandler.class);
    }

}