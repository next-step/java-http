package nextstep.org.apache.coyote.http11.requestline;

import org.apache.coyote.http11.request.HttpMethod;
import org.apache.coyote.http11.request.Path;
import org.apache.coyote.http11.request.QueryStrings;
import org.apache.coyote.http11.request.RequestLineParser;
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
        RequestLineParser requestLineParser = new RequestLineParser(REQUEST_WITH_STRING_QUERY);

        //when
        HttpMethod result = requestLineParser.getHttpMethod();

        //then
        assertThat(result).isEqualTo(HttpMethod.GET);
    }

    @DisplayName("프로토콜 파싱")
    @Test
    public void getProtocol() {
        //given
        RequestLineParser requestLineParser = new RequestLineParser(REQUEST_WITH_STRING_QUERY);

        //when
        String result = requestLineParser.getProtocol();

        //then
        assertThat(result).isEqualTo("HTTP");
    }

    @DisplayName("버전 파싱")
    @Test
    public void getVersion() {
        //given
        RequestLineParser requestLineParser = new RequestLineParser(REQUEST_WITH_STRING_QUERY);

        //when
        String result = requestLineParser.getVersion();

        //then
        assertThat(result).isEqualTo("1.1");
    }

    @DisplayName("RequestPath 파싱 - stringQuery가 없는 경우")
    @Test
    public void getRequestPath() {
        //given
        RequestLineParser requestLineParser = new RequestLineParser(REQUEST);

        //when
        Path path = requestLineParser.getPath();
        String result = path.urlPath();

        //then
        assertThat(result).isEqualTo("/users");
    }

    @DisplayName("RequestPath 파싱 - stringQuery가 있는 경우")
    @Test
    public void getRequestPathWithStringQuery() {
        //given
        RequestLineParser requestLineParser = new RequestLineParser(REQUEST_WITH_STRING_QUERY);

        //when
        Path path = requestLineParser.getPath();
        String result = path.urlPath();

        //then
        assertThat(result).isEqualTo("/users");
    }

    @DisplayName("StringQuery 파싱")
    @Test
    public void getStringQuery() {
        //given
        RequestLineParser requestLineParser = new RequestLineParser(REQUEST_WITH_STRING_QUERY);

        //when
        Path path = requestLineParser.getPath();
        QueryStrings result = path.queryStrings();

        //then
        assertAll(
                () -> assertThat(result.getQueryStringValueByKey("userId")).isEqualTo("javajigi"),
                () -> assertThat(result.getQueryStringValueByKey("password")).isEqualTo("password"),
                () -> assertThat(result.getQueryStringValueByKey("name")).isEqualTo("JaeSung")
        );
    }
}
