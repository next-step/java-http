package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {
    @Test
    void 있는파일을_경로로_넘길경우_해당파일의_객체를_생성한다() {
        // given
        String path = "/register.html";
        ClassLoader classLoader = getClass().getClassLoader();

        // when
        Resource resource = Resource.createResourceFromPath(path, classLoader);

        // then
        assertTrue(resource.getResourceFile().getName().contains("register.html"));
    }

    @Test
    void 없는파일을_경로로_넘길경우_404파일의_객체를_생성한다() {
        // given
        String path = "/없음.html";
        ClassLoader classLoader = getClass().getClassLoader();

        // when
        Resource resource = Resource.createResourceFromPath(path, classLoader);

        // then
        assertTrue(resource.getResourceFile().getName().contains("404.html"));
    }
}