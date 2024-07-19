package nextstep.org.apache.coyote.http11.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.coyote.http11.parser.HttpRequestDto;
import org.apache.coyote.http11.parser.HttpRequestParser;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.RequestMethod;
import org.apache.coyote.http11.request.factory.Http11FactoryProvider;
import org.apache.coyote.http11.request.factory.HttpRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


public class HttpRequestParserTest {

    @ParameterizedTest
    @EnumSource(value = RequestMethod.class)
    @DisplayName("GET 요청에 대한 HttpRequestMethod를 파싱한다.")
    void parseRequestLine(RequestMethod method) throws IOException {
        final String request = String.join("\r\n",
            "%s /index.html HTTP/1.1 ".formatted(method.toString()),
            "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "");
        final InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        final InputStream bufferedInputStream = new BufferedInputStream(inputStream);

        HttpRequestDto requestDto = HttpRequestParser.of(bufferedInputStream);

        assertAll(
            () -> assertThat(requestDto.getRequestMethod()).isEqualTo(method.toString()),
            () -> assertThat(requestDto.getRequestUrl()).isEqualTo("/index.html"),
            () -> assertThat(requestDto.getRequestProtocol()).isEqualTo("HTTP/1.1")
        );
    }

    @Test
    @DisplayName("Query String을 파싱한다.")
    void parseRequestLine() throws IOException {
        final String request = String.join("\r\n",
            "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1 " ,
                "Host: localhost:8080 ",
            "Connection: keep-alive ",
            "");
        final InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        final InputStream bufferedInputStream = new BufferedInputStream(inputStream);

        HttpRequestDto requestDto = HttpRequestParser.of(bufferedInputStream);
        Http11FactoryProvider factoryProvider = new Http11FactoryProvider();
        HttpRequestFactory httpRequestFactory = factoryProvider.provideFactory(
            requestDto.requestMethod);
        HttpRequest httpRequest = httpRequestFactory.createHttpInstance(requestDto);

        assertAll(
            () -> assertThat(httpRequest.getParams()).containsKey("userId"),
            () -> assertThat(httpRequest.getParams()).containsKey("password"),
            () -> assertThat(httpRequest.getParams()).containsKey("name"),
            () -> assertThat(httpRequest.getParams()).containsValues("javajigi", "password",
                "JaeSung")
        );
    }
}