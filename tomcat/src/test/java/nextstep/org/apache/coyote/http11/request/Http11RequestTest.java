package nextstep.org.apache.coyote.http11.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.coyote.http11.parser.HttpRequestDto;
import org.apache.coyote.http11.parser.HttpRequestParser;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.factory.Http11RequestFactoryProvider;
import org.apache.coyote.http11.request.factory.HttpRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Http11RequestTest {

    @DisplayName("Parsing된 값을 HttpRequest 값으로 Request Method, Url, Protocol, Version, Params를 가져옵니다.")
    @Test
    void constructor() throws IOException {
        final String request = String.join("\r\n",
            "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 ",
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "");
        final InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        final InputStreamReader bufferedInputStream = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(bufferedInputStream);

        HttpRequestDto requestDto = HttpRequestParser.parse(bufferedReader);
        Http11RequestFactoryProvider factoryProvider = new Http11RequestFactoryProvider();
        HttpRequestFactory httpRequestFactory = factoryProvider.provideFactory(
            requestDto.requestMethod);
        HttpRequest httpRequest = httpRequestFactory.createHttpInstance(requestDto);

        assertAll(
            () -> assertThat(httpRequest.getRequestMethod()).isEqualTo("GET"),
            () -> assertThat(httpRequest.getRequestUrl()).isEqualTo("/users"),
            () -> assertThat(httpRequest.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(httpRequest.getVersion()).isEqualTo("1.1"),
            () -> assertThat(httpRequest.getParams()).containsValues("javajigi", "password",
                "JaeSung")
        );
    }
}
