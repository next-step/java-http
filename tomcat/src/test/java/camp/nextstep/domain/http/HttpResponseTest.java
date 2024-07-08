package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpResponseTest {

    private static final HttpProtocol DEFAULT_HTTP_PROTOCOL = new HttpProtocol("HTTP/1.1");

    @Test
    void ok_response를_생성한다() {
        HttpResponse actual = HttpResponse.ok(DEFAULT_HTTP_PROTOCOL, emptyMap(), "Hello world!");
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void contentType을_받는_ok_response를_생성한다() {
        HttpResponse actual = HttpResponse.ok(
                DEFAULT_HTTP_PROTOCOL,
                ContentType.TEXT_HTML,
                emptyMap(),
                "Hello world!"
        );
        assertAll(
                () -> assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.OK),
                () -> assertThat(actual.getHttpHeaders()).contains(Map.entry("Content-Type", "text/html;charset=utf-8"))
        );
    }

    @Test
    void found_response를_생성한다() {
        HttpResponse actual = HttpResponse.found(DEFAULT_HTTP_PROTOCOL, "/index.html");
        assertAll(
                () -> assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.FOUND),
                () -> assertThat(actual.getHttpHeaders()).contains(Map.entry("Location", "/index.html"))
        );
    }

    @Test
    void header에_responseBody의_길이가_저장된다() {
        HttpResponse actual = new HttpResponse(DEFAULT_HTTP_PROTOCOL, HttpStatus.OK, emptyMap(), "Hello world!");
        assertThat(actual.getHttpHeaders()).contains(Map.entry("Content-Length", "12"));
    }

    @Test
    void responseBody가_빈값인_경우_길이가_header에_저장되지_않는다() {
        HttpResponse actual = new HttpResponse(DEFAULT_HTTP_PROTOCOL, HttpStatus.OK, emptyMap(), "");
        assertThat(actual.getHttpHeaders()).doesNotContainKey("Content-Length");
    }

    @Test
    void response_format에_맞추어_response를_반환한다() {
        String actual = new HttpResponse(
                DEFAULT_HTTP_PROTOCOL,
                HttpStatus.OK,
                Map.of("Content-Type", "text/html;charset=utf-8"),
                "Hello world!").buildResponse();
        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 12 ",
                "",
                "Hello world!");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void response_body가_빈값인_경우_format에_body연관_데이터없이_반환한다() {
        String actual = new HttpResponse(DEFAULT_HTTP_PROTOCOL, HttpStatus.FOUND, emptyMap(), "").buildResponse();
        String expected = "HTTP/1.1 302 Found ";
        assertThat(actual).isEqualTo(expected);
    }
}
