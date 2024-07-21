package nextstep.org.apache.coyote.http11.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.request.RequestProtocol;
import org.apache.coyote.http11.response.HttpResponseProtocol;
import org.apache.coyote.http11.response.HttpResponseVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class HttpResponseProtocolTest {

    @DisplayName("빈 Protocol Version은 생성에 실패합니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void failToCreateResponseProtocol(String version) {
        assertAll(
            () -> assertThatThrownBy(() -> new HttpResponseProtocol(version)).isInstanceOf(
                RuntimeException.class)
        );
    }

    @DisplayName("Http Version 에 따른, Response의 ResponseVersion과 Response Protocol 객체들을 생성합니다.")
    @ParameterizedTest
    @CsvSource({
        "1.1", "1.2"
    })
    void createResponseProtocol(String version) {
        HttpResponseProtocol requestProtocol = new HttpResponseProtocol(version);

        assertAll(
            () -> assertThat(requestProtocol.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestProtocol.getHttpVersion()).isEqualTo(
                new HttpResponseVersion(version))
        );

    }
}
