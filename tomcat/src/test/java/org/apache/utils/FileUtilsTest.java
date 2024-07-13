package org.apache.utils;

import java.io.IOException;
import org.apache.coyote.http11.meta.HttpPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileUtils 클래스는")
class FileUtilsTest {

    @DisplayName("파일이 존재하지 않을 때 빈 문자열을 반환한다.")
    @Test
    void readFileContent_File_Not_Exists() throws IOException {
        // given
        final var fileName = "not-exists.txt";

        // when
        final var result = FileUtils.getStaticFileContent(HttpPath.from(fileName));

        // then
        assertEquals("", result);
    }

    @DisplayName("파일을 읽어온다.")
    @Test
    void readFileContent() throws IOException {
        // given
        final var fileName = "nextstep.txt";

        // when
        final var result = FileUtils.getStaticFileContent(HttpPath.from(fileName));

        // then
        assertEquals("nextstep", result);
    }
}
