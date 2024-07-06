package camp.nextstep.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {
    @DisplayName("요구사항 1 - GET 요청")
    @Test
    public void test1() {
        //given
        String request = "GET /users HTTP/1.1";
        //when
        RequestLine requestLine = new RequestLine(request);
        //then
        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
                () -> assertThat(requestLine.getPath()).isEqualTo("/users"),
                () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }
}