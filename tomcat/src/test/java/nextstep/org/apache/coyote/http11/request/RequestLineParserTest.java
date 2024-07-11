package nextstep.org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestLineParserTest {
    private static String REQUEST = "GET /users HTTP/1.1";
    private static String REQUEST_WITH_STRING_QUERY = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

    @DisplayName("Http메소드 파싱")
    @Test
    public void getHttpMethod() {
        //given
        RequestLine requestLine = RequestLineParser.createRequestLine(REQUEST_WITH_STRING_QUERY);

        //when
        HttpMethod result = requestLine.getHttpMethod();

        //then
        assertThat(result).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("프로토콜 파싱")
    @Test
    public void getProtocol() {
        //given
        RequestLine requestLine = RequestLineParser.createRequestLine(REQUEST_WITH_STRING_QUERY);

        //when
        String result = requestLine.getProtocol();

        //then
        assertThat(result).isEqualTo("HTTP");
    }

    @DisplayName("버전 파싱")
    @Test
    public void getVersion() {
        //given
        RequestLine requestLine = RequestLineParser.createRequestLine(REQUEST_WITH_STRING_QUERY);

        //when
        String result = requestLine.getVersion();

        //then
        assertThat(result).isEqualTo("1.1");
    }

    @DisplayName("RequestPath 파싱 - stringQuery가 없는 경우")
    @Test
    public void getRequestPath() {
        //given
        RequestLine requestLine = RequestLineParser.createRequestLine(REQUEST);

        //when
        String result = requestLine.getUrlPath();

        //then
        assertThat(result).isEqualTo("/users");
    }

    @DisplayName("RequestPath 파싱 - stringQuery가 있는 경우")
    @Test
    public void getRequestPathWithStringQuery() {
        //given
        RequestLine requestLine = RequestLineParser.createRequestLine(REQUEST_WITH_STRING_QUERY);

        //when
        String result = requestLine.getUrlPath();

        //then
        assertThat(result).isEqualTo("/users");
    }

    @DisplayName("StringQuery 파싱")
    @Test
    public void getStringQuery() {
        //given
        RequestLine requestLine = RequestLineParser.createRequestLine(REQUEST_WITH_STRING_QUERY);

        //when
        QueryStrings result = requestLine.getQueryStrings();

        //then
        assertAll(
                () -> assertThat(result.getQueryStringValueByKey("userId")).isEqualTo("javajigi"),
                () -> assertThat(result.getQueryStringValueByKey("password")).isEqualTo("password"),
                () -> assertThat(result.getQueryStringValueByKey("name")).isEqualTo("JaeSung")
        );
    }
}
