package camp.nextstep.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestParserTest {
    private static String REQUEST = "GET /users HTTP/1.1";
    private static String REQUEST_WITH_STRING_QUERY = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

    @DisplayName("Http메소드 파싱")
    @Test
    public void getHttpMethod() {
        //given
        RequestParser requestParser = new RequestParser(REQUEST_WITH_STRING_QUERY);

        //when
        HttpMethod result = requestParser.getHttpMethod();

        //then
        assertThat(result).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("프로토콜 파싱")
    @Test
    public void getProtocol() {
        //given
        RequestParser requestParser = new RequestParser(REQUEST_WITH_STRING_QUERY);

        //when
        String result = requestParser.getProtocol();

        //then
        assertThat(result).isEqualTo("HTTP");
    }

    @DisplayName("버전 파싱")
    @Test
    public void getVersion() {
        //given
        RequestParser requestParser = new RequestParser(REQUEST_WITH_STRING_QUERY);

        //when
        String result = requestParser.getVersion();

        //then
        assertThat(result).isEqualTo("1.1");
    }

    @DisplayName("RequestPath 파싱 - stringQuery가 없는 경우")
    @Test
    public void getRequestPath() {
        //given
        RequestParser requestParser = new RequestParser(REQUEST);

        //when
        Path path = requestParser.getPath();
        String result = path.requestPath();

        //then
        assertThat(result).isEqualTo("/users");
    }

    @DisplayName("RequestPath 파싱 - stringQuery가 있는 경우")
    @Test
    public void getRequestPathWithStringQuery() {
        //given
        RequestParser requestParser = new RequestParser(REQUEST_WITH_STRING_QUERY);

        //when
        Path path = requestParser.getPath();
        String result = path.requestPath();

        //then
        assertThat(result).isEqualTo("/users");
    }

    @DisplayName("StringQuery 파싱")
    @Test
    public void getStringQuery() {
        //given
        RequestParser requestParser = new RequestParser(REQUEST_WITH_STRING_QUERY);

        //when
        Path path = requestParser.getPath();
        QueryStrings result = path.queryStrings();

        //then
        assertAll(
                () -> assertThat(result.getQueryStringValueByKey("userId")).isEqualTo("javajigi"),
                () -> assertThat(result.getQueryStringValueByKey("password")).isEqualTo("password"),
                () -> assertThat(result.getQueryStringValueByKey("name")).isEqualTo("JaeSung")
        );
    }
}
