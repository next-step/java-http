package nextstep.org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.request.HttpVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpVersionTest {

    @DisplayName("HTTP Version는 버젼을 가집니다. ")
    @Test
    void getVersion() {
        HttpVersion version = new HttpVersion("1.1");
        assertAll(
            () -> assertThat(version.getVersion()).isEqualTo("1.1")
        );
    }
}
