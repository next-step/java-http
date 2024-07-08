package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    void ok_response를_생성한다() {
        HttpResponse actual = HttpResponse.ok(new HttpProtocol("HTTP/1.1"));
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void found_response를_생성한다() {
        HttpResponse actual = HttpResponse.found(new HttpProtocol("HTTP/1.1"));
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}
