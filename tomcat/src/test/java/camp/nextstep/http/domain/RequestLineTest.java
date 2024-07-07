package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidRequestLineException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestLineTest {

    @Test
    void requestLine_에서_GET_Method_를_반환받을_수_있다() {
        final RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void requestLine_에서_POST_Method_를_반환받을_수_있다() {
        final RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void requestLine_에서_Path_를_반환받을_수_있다() {
        final RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getPath()).isEqualTo("/users");
    }

    @Test
    void requestLine_에서_Protocol_을_반환받을_수_있다() {
        final RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
    }

    @Test
    void requestLine_에서_Version_을_반환받을_수_있다() {
        final RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getVersion()).isEqualTo("1.1");
    }

    @Test
    void requestLine_형식이_맞지_않으면_예외가_발생한다() {
        assertThatThrownBy(() -> new RequestLine("GET /wrong"))
                .isInstanceOf(InvalidRequestLineException.class);
    }
}
