package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidStatusLineException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StatusLineTest {

    @Test
    void statusLine_에서_Version_을_반환받을_수_있다() {
        final StatusLine statusLine = new StatusLine("HTTP/1.1 200 OK");

        assertThat(statusLine.getVersion()).isEqualTo(new HttpVersion("HTTP/1.1"));
    }

    @Test
    void statusLine_에서_Status_Code_를_반환받을_수_있다() {
        final StatusLine statusLine = new StatusLine("HTTP/1.1 200 OK");

        assertThat(statusLine.getStatusCode()).isEqualTo(HttpStatusCode.OK);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "HTTP/1.1",
            "HTTP/1.1 200",
            "HTTP/1.1 OK",
            "HTTP/1.1 GOAT 200"
    })
    void statusLine_형식이_맞지_않으면_예외가_발생한다(final String input) {
        assertThatThrownBy(() -> new StatusLine(input))
                .isInstanceOf(InvalidStatusLineException.class);
    }

    @Test
    void statusLine_을_byte_로_변환할_수_있다() {
        final StatusLine statusLine = new StatusLine("HTTP/1.1 200 OK");

        assertThat(statusLine.getBytes()).isEqualTo("HTTP/1.1 200 OK".getBytes());
    }
}
