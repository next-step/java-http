package camp.nextstep.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestLineTest {

  @Test
  @DisplayName(" HTTP GET 요청에 대한 RequestLine을 파싱한다.")
  void parseHttpGetRequest() {
    // given
    final String request = "GET /users HTTP/1.1";

    // when
    final RequestLine requestLine = RequestLine.parse(request);

    // then
    assertThat(requestLine.getHttpMethod()).isEqualTo("GET");
    assertThat(requestLine.getPath()).isEqualTo("/users");
    assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
    assertThat(requestLine.getVersion()).isEqualTo("1.1");

  }


}