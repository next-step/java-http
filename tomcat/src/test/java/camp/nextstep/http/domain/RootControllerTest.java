package camp.nextstep.http.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.MockHttpRequestBuilder;
import support.MockHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class RootControllerTest {

    @Test
    @DisplayName("get 요청을 처리할 수 있다.")
    void getTest() throws Exception {
        final RootController rootController = new RootController();
        final HttpRequest httpRequest = new MockHttpRequestBuilder()
                .requestURI("/")
                .method(HttpMethod.GET)
                .build();
        final MockHttpResponse httpResponse = MockHttpResponse.create();

        rootController.doGet(httpRequest, httpResponse);

        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");
        assertThat(httpResponse.getOutputAsString()).isEqualTo(expected);
    }
}
