package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class HttpHeaderTest {
    @Test
    void 헤더가_들어올_경우_키의_개수에_맞게_map을_생성한다() throws IOException {
        // given
        String headerStr = "Host: www.nowhere123.com\n" +
                "Accept: image/gif, image/jpeg, */*\n" +
                "Accept-Language: en-us\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)";

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(headerStr.getBytes(StandardCharsets.UTF_8))
                )
        );

        // when
        HttpHeader httpHeader = HttpHeader.createHeadersFromReader(bufferedReader);

        // then
        assertTrue(httpHeader.getHttpHeaders().size() == 5);
    }
}