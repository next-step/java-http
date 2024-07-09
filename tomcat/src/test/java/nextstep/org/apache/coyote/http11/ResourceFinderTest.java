package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.ResourceFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ResourceFinderTest {
    @DisplayName("path 정보를 이용해 존재하는 리소스의 경로를 찾아준다.")
    @ParameterizedTest(name = "path = {0}")
    @ValueSource(strings = {"/index.html", "/css/styles.css"})
    void read(String path) {
        ResourceFinder resourceFinder = new ResourceFinder();

        File resource = resourceFinder.findByPath(path);

        assertThat(resource.getAbsolutePath()).endsWith("static" + path);
    }

    @DisplayName("path가 인덱스(/)인 경우, index.html을 찾아준다")
    @Test
    void readIndex() {
        ResourceFinder resourceFinder = new ResourceFinder();

        File resource = resourceFinder.findByPath("/");

        assertThat(resource.getAbsolutePath()).endsWith("static/index.html");
    }

    @DisplayName("path에 해당하는 파일이 없는 경우 예외를 발생시킨다.")
    @ParameterizedTest(name = "path = {0}")
    @ValueSource(strings = {"haha.html", "prevstep", "next.html.prev"})
    void readException(String path) {
        ResourceFinder resourceFinder = new ResourceFinder();
        assertThatThrownBy(() -> resourceFinder.findByPath(path))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
