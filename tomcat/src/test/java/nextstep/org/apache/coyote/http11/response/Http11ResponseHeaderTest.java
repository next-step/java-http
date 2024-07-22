package nextstep.org.apache.coyote.http11.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.apache.coyote.http11.response.Http11ResponseHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Http11ResponseHeaderTest {

    @DisplayName("HttpResponse는 Content-Type, Content-Length 헤더값을 가집니다.")
    @CsvSource(
        "html, 152"
    )
    @ParameterizedTest
    void getResponseHeader(String contentType, int responseBodySize) {
        Http11ResponseHeader http11ResponseHeader = new Http11ResponseHeader(contentType, responseBodySize);

        assertAll(
            () -> assertThat(http11ResponseHeader.toString()).isEqualTo(
                String.join("\r\n",
                    "Content-Type: text/html;charset=utf-8 ",
                    "Content-Length: 152 ")
            )
        );

    }
}
