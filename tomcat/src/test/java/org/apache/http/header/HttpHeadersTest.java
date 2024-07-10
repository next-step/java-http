package org.apache.http.header;

import org.apache.file.MediaType;
import org.apache.http.body.HttpFormBody;
import org.apache.http.body.HttpTextBody;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void messages_to_headers() {
        var messages = List.of("Content-Type: application/x-www-form-urlencoded;charset=utf-8");

        var actual = new HttpHeaders(messages);

        var expected = new HttpHeaders().add(new ContentType(MediaType.FORM_URL_ENCODED));
        Assertions.assertThat(actual).hasToString(expected.toString());
    }

    @Test
    void messages_parse_body_form() {
        var headers = new HttpHeaders().add(new ContentType(MediaType.FORM_URL_ENCODED));
        var messages = "test=hi";

        var actual = headers.parseBody(messages);

        Assertions.assertThat(actual).isInstanceOf(HttpFormBody.class);
    }

    @Test
    void messages_parse_body_text() {
        var headers = new HttpHeaders().add(new ContentType(MediaType.TEXT_HTML));
        var messages = "test=hi";

        var actual = headers.parseBody(messages);

        Assertions.assertThat(actual).isInstanceOf(HttpTextBody.class);
    }

    @Test
    void messages_parse_body_null() {
        var headers = new HttpHeaders();
        var messages = "test=hi";

        var actual = headers.parseBody(messages);

        Assertions.assertThat(actual).isNull();
    }

}