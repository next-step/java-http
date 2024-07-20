package org.apache.coyote.http11;

import org.apache.coyote.http11.request.HttpRequestLineInvalidException;
import org.apache.coyote.http11.request.RequestParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;


class RequestParserTest {

    @Test
    @DisplayName("파싱할 데이터가 없을 경우 RequestLine 생성에 실패한다")
    public void inputStreamIsEmptyTest() {

        Assertions.assertThatThrownBy(() -> RequestParser.parse(new ByteArrayInputStream(new byte[]{})))
                .isInstanceOf(HttpRequestLineInvalidException.class)
                .hasMessage("RequestLine is null or empty: null");
    }


}