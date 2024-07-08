package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpQueryParamsTest {

    @Nested
    class From{

        @Test
        @DisplayName("유효한 형태의 queryString 일 경우 key, value 에 맞춰 제대로 파싱한다")
        void success() {
            String input = "userId=javajigi&password=password&name=JaeSung";

            HttpQueryParams result = HttpQueryParams.from(input);

            assertThat(result.value().get("userId")).isEqualTo("javajigi");
            assertThat(result.value().get("password")).isEqualTo("password");
            assertThat(result.value().get("name")).isEqualTo("JaeSung");
        }
    }

}