package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {

    @Test
    void RequestLine에_status_GET이_없으면_예외가_발생한다() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new RequestLine("ERROR"));
    }
}