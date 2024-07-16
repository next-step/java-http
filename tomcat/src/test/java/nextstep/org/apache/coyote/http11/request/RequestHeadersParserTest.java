package nextstep.org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.model.RequestHeaders;
import org.apache.coyote.http11.request.parser.RequestHeadersParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestHeadersParserTest {

    @DisplayName("RequestHeaders 파싱하기")
    @Test
    public void parseRequestHeaders () {
        //given
        String testData =
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 17\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "\n" +
                "name=hello&age=40";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(testData));
        List<String> requestHeaders = bufferedReader.lines().takeWhile(line -> !line.isEmpty()).toList();

        //when
        RequestHeaders result = RequestHeadersParser.parse(requestHeaders);
        //then
        assertAll(
                () -> assertThat(result.getContentLength()).isEqualTo(17)
        );
    }
}