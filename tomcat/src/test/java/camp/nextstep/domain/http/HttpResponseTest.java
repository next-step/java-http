package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void ok_response를_생성한다() {
        HttpResponse actual = HttpResponse.ok(new HttpProtocol("HTTP/1.1"), "Hello world!");
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void found_response를_생성한다() {
        HttpResponse actual = HttpResponse.found(new HttpProtocol("HTTP/1.1"), "Hello world!");
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void header에_responseBody의_길이가_저장된다() {
        HttpResponse actual = new HttpResponse(new HttpProtocol("HTTP/1.1"), HttpStatus.OK, "Hello world!");
        assertThat(actual.getHttpHeaders()).contains(Map.entry("Content-Length", "12"));
    }
}
