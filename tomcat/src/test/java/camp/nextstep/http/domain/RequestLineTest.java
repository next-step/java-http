package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestLineTest {

    @Test
    void requestLine_에서_Method_를_반환받을_수_있다() {
        final RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getMethod()).isEqualTo("GET");
    }

    @Test
    void requestLine_에서_Path_를_반환받을_수_있다() {
        final RequestLine requestLine = new RequestLine("GET /users HTTP/1.1");

        assertThat(requestLine.getPath()).isEqualTo("/users");
    }
}
