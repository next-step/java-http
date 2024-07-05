package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpProtocolTest {

    @Test
    void protocol과_version이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpProtocol("HTTP"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("HttpProtocol값이 정상적으로 입력되지 않았습니다 - HTTP");
    }

    @Test
    void protocol과_version을_파싱한다() {
        HttpProtocol actual = new HttpProtocol("HTTP/1.1");
        assertAll(
                () -> assertThat(actual.getProtocol()).isEqualTo("HTTP"),
                () -> assertThat(actual.getVersion()).isEqualTo("1.1")
        );
    }
}