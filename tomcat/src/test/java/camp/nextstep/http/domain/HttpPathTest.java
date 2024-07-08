package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {

    @Test
    void httpPath_를_생성할_수_있다() {
        final HttpPath httpPath = new HttpPath("/users");

        assertThat(httpPath.getPath()).isEqualTo("/users");
    }

}
