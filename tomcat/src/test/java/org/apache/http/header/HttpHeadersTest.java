package org.apache.http.header;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HttpHeadersTest {

    @Test
    void headers_string_consist_order() {
        var headers = new HttpHeaders()
                .add(new ContentType(MediaType.TEXT_HTML))
                .add(new ContentLength(1));

        var reverseHeaders = new HttpHeaders()
                .add(new ContentLength(1))
                .add(new ContentType(MediaType.TEXT_HTML));

        Assertions.assertThat(headers.toString()).isEqualTo(reverseHeaders.toString());
    }

}