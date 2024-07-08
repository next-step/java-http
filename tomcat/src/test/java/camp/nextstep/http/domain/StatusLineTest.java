package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {

    @Test
    void statusLine_에서_Version_을_반환받을_수_있다() {
        final StatusLine statusLine = StatusLine.createOk();

        assertThat(statusLine.getVersion()).isEqualTo(new HttpVersion("HTTP/1.1"));
    }

    @Test
    void statusLine_에서_Status_Code_를_반환받을_수_있다() {
        final StatusLine statusLine = StatusLine.createOk();

        assertThat(statusLine.getStatusCode()).isEqualTo(HttpStatusCode.OK);
    }

    @Test
    void statusLine_을_String_로_변환할_수_있다() {
        final StatusLine statusLine = StatusLine.createOk();

        assertThat(statusLine.convertToString()).isEqualTo("HTTP/1.1 200 OK ");
    }
}
