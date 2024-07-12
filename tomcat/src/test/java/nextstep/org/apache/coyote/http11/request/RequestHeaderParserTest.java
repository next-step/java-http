package nextstep.org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.RequestHeaderParser;
import org.apache.coyote.http11.request.model.HttpMethod;
import org.apache.coyote.http11.request.model.RequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestHeaderParserTest {

    @DisplayName("RequestHeader 파싱하기")
    @Test
    public void parseRequestHeader () {
        //given
        String testData = "POST /users HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 17\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "\n" +
                "name=hello&age=40";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(testData));
        List<String> requestHeaders = bufferedReader.lines().takeWhile(line -> !line.isEmpty()).toList();

        //when
        RequestHeader result = RequestHeaderParser.parse(bufferedReader, requestHeaders);
        //then
        assertAll(
                () -> assertThat(result.getRequestLine().getHttpMethod()).isEqualTo(HttpMethod.POST),
                () -> assertThat(result.getRequestLine().getUrlPath()).isEqualTo("/users"),
                () -> assertThat(result.getRequestLine().getVersion()).isEqualTo("1.1"),
                () -> assertThat(result.getRequestBodies().getRequestBodyValueByKey("name")).isEqualTo("hello"),
                () -> assertThat(result.getRequestBodies().getRequestBodyValueByKey("age")).isEqualTo("40")
        );
    }
}