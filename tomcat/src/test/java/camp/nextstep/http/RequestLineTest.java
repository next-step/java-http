package camp.nextstep.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

import camp.nextstep.exception.InvalidRequestException;
import java.util.Map;
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
    assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
    assertThat(requestLine.getPath()).isEqualTo("/users");
    assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
    assertThat(requestLine.getVersion()).isEqualTo("1.1");
  }

  @Test
  @DisplayName(" HTTP POST 요청에 대한 RequestLine을 파싱한다.")
  void parseHttpPostRequest() {
    // given
    final String request = "POST /users HTTP/1.1";

    // when
    final RequestLine requestLine = RequestLine.parse(request);

    // then
    assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.POST);
    assertThat(requestLine.getPath()).isEqualTo("/users");
    assertThat(requestLine.getProtocol()).isEqualTo("HTTP");
    assertThat(requestLine.getVersion()).isEqualTo("1.1");
  }

  @Test
  @DisplayName(" HTTP 요청이 빈 값일때 InvalidRequestException 에러를 리턴한다.")
  void parseEmptyRequest() {
    // given
    final String request = "";

    // when
    Throwable thrown = catchThrowable(() -> RequestLine.parse(request));

    // then
    assertThat(thrown)
        .isInstanceOf(InvalidRequestException.class);
  }

  @Test
  @DisplayName(" HTTP 요청의 Query String으로 전달되는 데이터를 파싱한다.")
  void parseHttpQueryStringRequest() {
    // given
    final String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

    // when
    final RequestLine requestLine = RequestLine.parse(request);

    // then
    assertThat(requestLine.getHttpMethod()).isInstanceOf(HttpMethod.class);
    assertThat(requestLine.getQueryString()).isEqualTo(
        Map.of("userId", "javajigi", "password", "password", "name", "JaeSung"));
  }

  @Test
  @DisplayName(" HTTP 요청의 Query String으로 전달되는 데이터를 파싱한다.")
  void parseHttpSingleQueryStringRequest() {
    // given
    final String request = "GET /users?userId=javajigi HTTP/1.1";

    // when
    final RequestLine requestLine = RequestLine.parse(request);

    // then
    assertThat(requestLine.getHttpMethod()).isInstanceOf(HttpMethod.class);
    assertThat(requestLine.getQueryString()).isEqualTo(
        Map.of("userId", "javajigi"));
  }

}