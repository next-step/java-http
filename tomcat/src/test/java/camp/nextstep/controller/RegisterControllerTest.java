package camp.nextstep.controller;

import camp.nextstep.http.domain.HttpMethod;
import camp.nextstep.http.domain.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.MockHttpRequestBuilder;
import support.MockHttpResponse;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterControllerTest {

    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        registerController = new RegisterController();
    }

    @Test
    @DisplayName("get 요청시 register 페이지를 응답한다.")
    void getTest() throws Exception {
        final HttpRequest httpRequest = new MockHttpRequestBuilder()
                .requestURI("/register")
                .method(HttpMethod.GET)
                .build();
        final MockHttpResponse httpResponse = MockHttpResponse.create();

        registerController.doGet(httpRequest, httpResponse);

        final URL resource = getClass().getClassLoader().getResource("static/register.html");
        final var expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 4391 ",
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));

        assertThat(httpResponse.getOutputAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("post 요청시 index 리다이렉트 응답을 반환한다.")
    void postTest() throws Exception {
        final HttpRequest httpRequest = new MockHttpRequestBuilder()
                .requestURI("/register")
                .method(HttpMethod.POST)
                .addBody("account", "gugu")
                .addBody("password", "password")
                .build();
        final MockHttpResponse httpResponse = MockHttpResponse.create();

        registerController.doPost(httpRequest, httpResponse);

        final var expected = String.join("\r\n",
                "HTTP/1.1 302 Found ",
                "Location: /index.html ",
                "",
                "");

        assertThat(httpResponse.getOutputAsString()).isEqualTo(expected);
    }
}
