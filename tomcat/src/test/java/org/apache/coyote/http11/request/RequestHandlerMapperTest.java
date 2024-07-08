package org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.handler.DefaultHandler;
import org.apache.coyote.http11.request.handler.LoginHandler;
import org.apache.coyote.http11.request.handler.NotFoundHandler;
import org.apache.coyote.http11.request.handler.RequestHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerMapperTest {

    static Stream<Arguments> methodSource() {
        return Stream.of(
                Arguments.of("/index.html", new DefaultHandler()),
                Arguments.of("/login.html", new LoginHandler()),
                Arguments.of("/static.css", new DefaultHandler()),
                Arguments.of("/js.js", new DefaultHandler()),
                Arguments.of("", new NotFoundHandler())
        );
    }

    @DisplayName("RequestHandlerMapper에서 path로 적절한 RequestHandler를 찾는다.")
    @ParameterizedTest
    @MethodSource("methodSource")
    void mappingTest(final String path, final RequestHandler expect) {
        // given
        final RequestHandlerMapper requestHandlerMapper = RequestHandlerMapper.getInstance();

        // when
        final RequestHandler result = requestHandlerMapper.getHandler(path);

        // then
        assertThat(result.getClass()).isEqualTo(expect.getClass());
    }
}
