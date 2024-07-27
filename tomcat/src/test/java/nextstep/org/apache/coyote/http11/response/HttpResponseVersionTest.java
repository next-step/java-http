package nextstep.org.apache.coyote.http11.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.response.statusline.HttpResponseVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseVersionTest {

    @DisplayName("Response Version 객체를 생성합니다.")
    @Test
    void getVersion() {
        HttpResponseVersion version = new HttpResponseVersion("1.1");
        assertAll(
            () -> assertThat(version.toString()).isEqualTo("1.1")
        );
    }
}
