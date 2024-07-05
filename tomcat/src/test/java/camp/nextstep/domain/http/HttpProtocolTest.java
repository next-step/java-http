package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpProtocolTest {

    @Test
    void protocol과_version이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HttpProtocol("HTTP"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("HttpProtocol값이 정상적으로 입력되지 않았습니다 - HTTP");
    }
}