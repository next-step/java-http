package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticResourceTest {
    @Test
    void 있는파일을_경로로_넘길경우_해당파일의_객체를_생성한다() {
        // given
        String path = "/register.html";

        // when
        StaticResource staticResource = StaticResource.createResourceFromPath(path, getClass().getClassLoader());

        // then
        assertTrue(staticResource.getResourceFile().getName().contains("register.html"));
    }

    @Test
    void 없는파일을_경로로_넘길경우_404파일의_객체를_생성한다() {
        // given
        String path = "/없음.html";

        // when
        StaticResource staticResource = StaticResource.createResourceFromPath(path, getClass().getClassLoader());

        // then
        assertTrue(staticResource.getResourceFile().getName().contains("404.html"));
    }
}
