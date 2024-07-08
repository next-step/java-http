package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpStatusException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpStatusCodeTest {

    @Test
    void HttpStatus_OK를_정수_200을_통해_생성할_수_있다() {
        final HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(200);

        assertThat(httpStatusCode).isEqualTo(HttpStatusCode.OK);
    }

    @ParameterizedTest
    @ValueSource(ints = {99, 1000})
    void HttpStatus_는_세자리_정수가_아니면_예외를_던진다(final int value) {
        assertThatThrownBy(() -> HttpStatusCode.valueOf(value))
                .isInstanceOf(InvalidHttpStatusException.class);
    }
}
