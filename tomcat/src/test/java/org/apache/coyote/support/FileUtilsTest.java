package org.apache.coyote.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    @Test
    @DisplayName("파일 path 로 부터 확장자를 추출한다")
    void extractExtension() {
        String filePath = "Index.html";

        String actual = FileUtils.extractExtension(filePath);

        assertThat(actual).isEqualTo("html");
    }

}