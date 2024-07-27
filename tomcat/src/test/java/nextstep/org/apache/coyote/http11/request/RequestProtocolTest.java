package nextstep.org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.request.requestline.HttpVersion;
import org.apache.coyote.http11.request.requestline.RequestProtocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class RequestProtocolTest {

    @DisplayName("RequestLine을 받아서 RequestVersion과 Protocol 객체들을 생성합니다.")
    @ParameterizedTest
    @CsvSource({
        "HTTP, 1.1", "HTTP, 1.2"
    })
    void getProtocolAndVersion(String protocol, String version) {
        String parsedLine = protocol + "/" + version;
        RequestProtocol requestProtocol = new RequestProtocol(parsedLine);

        assertAll(
            () -> assertThat(requestProtocol.getProtocol()).isEqualTo(protocol),
            () -> assertThat(requestProtocol.getHttpVersion()).isEqualTo(new HttpVersion(version))
        );
    }

    @DisplayName("잘못된 RequestProtocol은 객체 생성에 실패한다.")
    @ParameterizedTest
    @CsvSource({
        "HTTTP, 1.1", "HTTP, 1.2"
    })
    void failBadRequestLine(String protocol, String version) {
        String parsedLine = protocol + "/" + version + "/" + version;

        assertAll(
            () -> assertThatThrownBy(() -> new RequestProtocol(parsedLine)).isInstanceOf(
                RuntimeException.class)
        );
    }

    @DisplayName("빈 RequestProtocol은 객체 생성에 실패한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void failEmptyRequestLine(String requestLine) {

        assertAll(
            () -> assertThatThrownBy(() -> new RequestProtocol(requestLine)).isInstanceOf(
                RuntimeException.class)
        );
    }
}
