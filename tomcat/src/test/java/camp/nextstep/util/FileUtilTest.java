package camp.nextstep.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileUtilTest {

    @Test
    void 존재하지_않는_파일을_요청한_경우_에외가_발생한다() {
        assertThatThrownBy(() -> FileUtil.getStaticPathFile("/error-path.html", getClass()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 file 경로입니다. - static/error-path.html");
    }

    @Test
    void 요청된_경로의_파일을_반환한다() {
        File actual = FileUtil.getStaticPathFile("/test.html", getClass());
        assertThat(actual.getName()).isEqualTo("test.html");
    }
}
