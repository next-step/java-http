package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {
    @Test
    void 쿼리파라미터가_있을_경우_정확히_파싱한다() {
        // given
        String url = "/login?account=gugu&password=password";

        // when
        Path path = Path.createPathByPathStr(url);

        // then
        assertTrue(path.getQueryParams().size() == 2);
        assertTrue(path.getQueryParams().get("account").equals("gugu"));
    }
}