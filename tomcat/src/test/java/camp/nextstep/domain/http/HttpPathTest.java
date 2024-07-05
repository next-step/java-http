package camp.nextstep.domain.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {

    @Test
    void query_string이_없는_경우_path만_파싱한다() {
        HttpPath actual = new HttpPath("/docs/index.html");
        assertThat(actual.getPath()).isEqualTo("/docs/index.html");
    }
}