package org.apache.http.header;

import org.apache.file.MediaType;
import org.apache.http.body.HttpFormBody;
import org.apache.http.body.HttpTextBody;
import org.apache.http.session.HttpSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class HttpRequestHeadersTest {

    @Test
    void messages_to_headers() {
        var messages = List.of("Content-Type: application/x-www-form-urlencoded;charset=utf-8");

        var actual = new HttpRequestHeaders(messages);

        var expected = new HttpRequestHeaders(new ContentType(MediaType.FORM_URL_ENCODED));
        Assertions.assertThat(actual).hasToString(expected.toString());
    }

    @Test
    void messages_parse_body_form() {
        var headers = new HttpRequestHeaders(new ContentType(MediaType.FORM_URL_ENCODED));
        var messages = "test=hi";

        var actual = headers.parseBody(messages);

        Assertions.assertThat(actual).isInstanceOf(HttpFormBody.class);
    }

    @Test
    void messages_parse_body_text() {
        var headers = new HttpRequestHeaders(new ContentType(MediaType.TEXT_HTML));
        var messages = "test=hi";

        var actual = headers.parseBody(messages);

        Assertions.assertThat(actual).isInstanceOf(HttpTextBody.class);
    }

    @Test
    void messages_parse_body_null() {
        var headers = new HttpRequestHeaders();
        var messages = "test=hi";

        var actual = headers.parseBody(messages);

        Assertions.assertThat(actual).isNull();
    }

    @Test
    void session_from_cookie() {
        var headers = new HttpRequestHeaders(new Cookie("JSESSIONID=cookie"));

        var actual = headers.getSession();

        Assertions.assertThat(actual).isEqualTo(new HttpSession("cookie"));
    }

    @Test
    void session_from_empty_cookie() {
        var headers = new HttpRequestHeaders();

        var actual = headers.getSession();

        Assertions.assertThat(actual).isNull();
    }
}