package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    private static final HttpProtocol DEFAULT_HTTP_PROTOCOL = new HttpProtocol("HTTP/1.1");

    @Test
    void ok_response를_생성한다() {
        HttpResponse actual = HttpResponse.ok(DEFAULT_HTTP_PROTOCOL, "Hello world!");
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void found_response를_생성한다() {
        HttpResponse actual = HttpResponse.found(DEFAULT_HTTP_PROTOCOL);
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void header에_responseBody의_길이가_저장된다() {
        HttpResponse actual = new HttpResponse(DEFAULT_HTTP_PROTOCOL, HttpStatus.OK, "Hello world!");
        assertThat(actual.getHttpHeaders()).contains(Map.entry("Content-Length", "12"));
    }

    @Test
    void responseBody가_빈값인_경우_길이가_header에_저장되지_않는다() {
        HttpResponse actual = new HttpResponse(DEFAULT_HTTP_PROTOCOL, HttpStatus.OK, "");
        assertThat(actual.getHttpHeaders()).doesNotContainKey("Content-Length");
    }
}
