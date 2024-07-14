package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.RequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RequestBodyParserTest {

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
        headers = Mockito.mock(HttpHeaders.class);
    }

    @Test
    @DisplayName("헤더에 Content-Length 가 있는 경우 request body 를 파싱한다")
    void parse_withContentLength() throws IOException {
        final String requestBody = "key1=value1&key2=value2";
        BufferedReader br = new BufferedReader(new StringReader(requestBody));
        when(headers.hasContentLength()).thenReturn(true);
        when(headers.contentLength()).thenReturn(requestBody.length());

        RequestBody actual = RequestBodyParser.parse(br, headers);

        assertEquals(2, actual.map().size());
        assertEquals("value1", actual.get("key1"));
        assertEquals("value2", actual.get("key2"));
    }

    @Test
    @DisplayName("헤더에 Content-Length 가 존재하지 않는 경우 request body 를 파싱하지 않는다")
    void parse_withoutContentLength() throws IOException {
        String requestBody = "key1=value1&key2=value2";
        BufferedReader br = new BufferedReader(new StringReader(requestBody));
        when(headers.hasContentLength()).thenReturn(false);

        RequestBody actual = RequestBodyParser.parse(br, headers);

        assertEquals(0, actual.map().size());
    }

}