package nextstep.org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RequestMethodTest {

    @DisplayName("정상적인 REQUEST METHOD를 생성합니다.")
    @ParameterizedTest
    @CsvSource({
        "GET", "POST"
    })
    void createRequestMethod(String requestMethod) {
        RequestMethod requestMethodEnum = RequestMethod.valueOf(requestMethod);
        assertAll(
            () -> assertThat(requestMethodEnum.name()).isEqualTo(requestMethod)
        );
    }

    @DisplayName("비정상적인 REQUEST METHOD를 생성에 실패합니다.")
    @ParameterizedTest()
    @CsvSource({
        "hello", "HI"
    })
    void failUnavailableRequestMethod(String requestMethod) {

        assertAll(
            () -> assertThatThrownBy(() -> RequestMethod.valueOf(requestMethod))
                .isInstanceOf(RuntimeException.class)
        );
    }
}
