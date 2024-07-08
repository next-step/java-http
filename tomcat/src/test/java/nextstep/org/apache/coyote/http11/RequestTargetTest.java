package nextstep.org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTargetTest {
    static class RequestTarget {
        private final String path;
        private final Map<String, String> queryStringMap;

        private RequestTarget(final String path, final Map<String, String> queryStringMap) {
            this.path = path;
            this.queryStringMap = queryStringMap;
        }

        public static RequestTarget from(String requestTarget) {
            return new RequestTarget("", Map.of());
        }
    }

    @DisplayName("String requestTarget에 쿼리 스트링이 없을 경우 path만 가진 객체가 생성된다.")
    @Test
    void from() {
        String requestTargetStr = "/users";

        RequestTarget requestTarget = RequestTarget.from(requestTargetStr);

        assertThat(requestTarget).extracting("path").isEqualTo(requestTargetStr);
        assertThat(requestTarget).extracting("queryStringMap").isEqualTo(Map.of());
    }

    @DisplayName("String requestTarget에 쿼리 스트링이 존재할 경우 path와 queryString을 모두 가진 객체가 생성된다.")
    @Test
    void from2() {
        String requestTargetStr = "/users?name=kim";

        RequestTarget requestTarget = RequestTarget.from(requestTargetStr);

        assertThat(requestTarget).extracting("path").isEqualTo(requestTargetStr);
        assertThat(requestTarget).extracting("queryStringMap").isEqualTo(Map.of("name", "kim"));
    }

    @DisplayName("쿼리 스트링은 &를 기준으로 분리되어 key = value 형태로 저장된다.")
    @Test
    void queryString() {
        String requestTargetStr = "/users?name=kim&age=100";

        RequestTarget requestTarget = RequestTarget.from(requestTargetStr);

        assertThat(requestTarget).extracting("queryStringMap").isEqualTo(Map.of(
                "name", "kim",
                "age", "100"
        ));
    }
}

