package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {

    @Test
    void statusLine_에서_Version_을_반환받을_수_있다() {
        final StatusLine statusLine = new StatusLine("HTTP/1.1 200 OK");

        assertThat(statusLine.getVersion()).isEqualTo(new HttpVersion("HTTP/1.1"));
    }

    @Test
    void statusLine_에서_Status_Code_를_반환받을_수_있다() {
        final StatusLine statusLine = new StatusLine("HTTP/1.1 200 OK");

        assertThat(statusLine.getStatusCode()).isEqualTo(200);
    }

    @Test
    void statusLine_에서_Reason_Phase_를_반환받을_수_있다() {
        final StatusLine statusLine = new StatusLine("HTTP/1.1 200 OK");

        assertThat(statusLine.getReasonPhase()).isEqualTo("OK");
    }
}
