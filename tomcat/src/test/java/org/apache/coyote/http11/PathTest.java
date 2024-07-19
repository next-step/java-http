package org.apache.coyote.http11;

import org.apache.coyote.http11.request.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class PathTest {

    @Test
    @DisplayName("쿼리 스트링이 없는 경우 queryParamMap을 조회할 경우 빈 Map을 반환한다")
    public void queryParamEmptyTest() {

        final var path = new Path("GET /users HTTP/1.1");

        assertThat(path.getQueryString()).isEmpty();
    }

}