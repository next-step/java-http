package nextstep.org.apache.coyote.http11.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.response.StatusCode;
import org.apache.coyote.http11.response.StatusLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StatusLineTest {

    @DisplayName("StatusLine 객체를 생성합니다.")
    @CsvSource(
        "1.1, OK"
    )
    @ParameterizedTest
    void createStatusLine(String version, String statusCode) {
        StatusLine statusLine = new StatusLine(version, statusCode);
        assertAll(
            () -> assertThat(statusLine.toString()).isEqualTo("HTTP/"+ version + " " + StatusCode.valueOf(statusCode) + " ")
        );
    }

    @DisplayName("빈 값이 주어지면 StatusLine 객체 생성에 실패합니다.")
    @Test
    void failToCreateStatusLineWithEmpty() {
        String version = "";
        String statusCode =  "";
        assertAll(
            () -> assertThatThrownBy(() -> new StatusLine(version, statusCode))
                .isInstanceOf(RuntimeException.class)
        );
    }
}
