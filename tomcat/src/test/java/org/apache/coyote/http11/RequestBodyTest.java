package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {
    @DisplayName("a=b&c=d 형태의 값을 받아서 Map에 key=value 쌍으로 저장한다")
    @Test
    void addBody() {
        String body = "account=kim&password=nextstep";
        RequestBody requestBody = RequestBody.empty();

        requestBody.addBody(body);

        assertThat(requestBody.getValue("account")).isEqualTo("kim");
        assertThat(requestBody.getValue("password")).isEqualTo("nextstep");
    }
}