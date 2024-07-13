package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.HttpProtocol;
import org.apache.coyote.http11.InvalidHttpProtocolException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpProtocolTest {
    @DisplayName("HTTP/1.1 String 값을 받아 객체를 생성한다")
    @Test
    void from() {
        HttpProtocol httpProtocol = HttpProtocol.from("HTTP/1.1");

        assertThat(httpProtocol.getVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("프로토콜이 HTTP가 아니면 예외를 발생시킨다.")
    @ParameterizedTest(name = "protocol = {0}")
    @ValueSource(strings = {"FTP", "SMTP"})
    void fromException(String version) {
        assertThatThrownBy(() -> HttpProtocol.from(version))
                .isInstanceOf(InvalidHttpProtocolException.class);
    }

    @DisplayName("지원하지 않는 버전이면 예외를 발생시킨다")
    @ParameterizedTest(name = "version = {0}")
    @ValueSource(strings = {"HTTP/0.9", "HTTP/2", "HTTP/3"})
    void fromException2(String version) {
        assertThatThrownBy(() -> HttpProtocol.from(version))
                .isInstanceOf(InvalidHttpProtocolException.class);
    }
}