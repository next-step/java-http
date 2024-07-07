package camp.nextstep.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileUtilTest {

    @Test
    void 존재하지_않는_파일을_요청한_경우_에외가_발생한다() {
        assertThatThrownBy(() -> FileUtil.readStaticPathFileResource("/error-path.html", getClass()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 file 경로입니다. - static/error-path.html");
    }

    @Test
    void 요청된_경로의_파일의_데이터를_반환한다() throws IOException {
        String actual = FileUtil.readStaticPathFileResource("/nextstep.txt", getClass());
        assertThat(actual).isEqualTo("nextstep");
    }

    @Test
    void 파일의_확장자를_반환한다() {
        String actual = FileUtil.parseExtension("/index.html");
        assertThat(actual).isEqualTo("html");
    }
}
