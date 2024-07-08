package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.ResourceReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ResourceReaderTest {
    @DisplayName("path 정보를 이용해 존재하는 리소스의 경로를 찾아준다.")
    @ParameterizedTest(name = "path = {0}")
    @CsvSource({
            "/index.html,css",
            "/css/styles.css,@charset \"UTF-8\""
    })
    void read(String path, String content) {
        ResourceReader resourceReader = new ResourceReader();

        String resourcePath = resourceReader.read(path);

        assertThat(resourcePath).contains(content);
    }

    @DisplayName("path가 인덱스(/)인 경우, index.html을 찾아준다")
    @Test
    void readIndex() {
        ResourceReader resourceReader = new ResourceReader();

        String resourcePath = resourceReader.read("/");

        assertThat(resourcePath).contains("css");
    }

    @DisplayName("path에 해당하는 파일이 없는 경우 예외를 발생시킨다.")
    @ParameterizedTest(name = "path = {0}")
    @ValueSource(strings = {"haha.html", "prevstep", "next.html.prev"})
    void readException(String path) {
        ResourceReader resourceReader = new ResourceReader();
        assertThatThrownBy(() -> resourceReader.read(path))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
