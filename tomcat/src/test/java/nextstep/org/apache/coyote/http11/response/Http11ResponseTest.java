package nextstep.org.apache.coyote.http11.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.IOException;
import org.apache.coyote.http11.request.RequestMethod;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.StatusLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Http11ResponseTest {

    @DisplayName("httpResponse를 정상적으로 생성합니다.")
    @Test
    void getProtocol() throws IOException {
        String message = "Hello world!";

        HttpResponse httpResponse = new Http11Response.HttpResponseBuilder()
            .statusLine("1.1", "OK")
            .responseHeader("text/html", message.getBytes().length)
            .messageBody(message.getBytes())
            .build();

        assertAll(
            () -> assertThat(httpResponse.getMessageBody()).isEqualTo(message.getBytes()),
            () -> assertThat(httpResponse.getStatusLine()).isEqualTo(new StatusLine("1.1", "OK"))
        );

    }

}
