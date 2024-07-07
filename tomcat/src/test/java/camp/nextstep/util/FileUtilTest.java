package camp.nextstep.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileUtilTest {

    @Test
    void 존재하지_않는_파일을_요청한_경우_에외가_발생한다() {
        assertThatThrownBy(() -> FileUtil.getFile("/error-path.html", getClass()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 file 경로입니다. - /error-path.html");
    }
}
