package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class RequestBodyTest {

    @Test
    void RequestBody_를_통해_값을_추출_할_수_있다() {
        final RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=JaeSung");

        assertSoftly(softly -> {
            softly.assertThat(requestBody.get("userId")).isEqualTo("javajigi");
            softly.assertThat(requestBody.get("password")).isEqualTo("password");
            softly.assertThat(requestBody.get("name")).isEqualTo("JaeSung");
        });
    }

    @Test
    void RequestBody_에_없는_값을_요청하면_null_을_반환한다() {
        final RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=JaeSung");

        assertThat(requestBody.get("none")).isNull();
    }

    @Test
    void RequestBody_의_형식이_맞지_않으면_null_을_반환한다() {
        final RequestBody requestBody = new RequestBody("userId=javajigi&password:password");

        assertThat(requestBody.get("password")).isNull();
    }
}
