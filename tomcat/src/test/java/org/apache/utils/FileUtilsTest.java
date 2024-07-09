package org.apache.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileUtils 클래스는")
class FileUtilsTest {

    @DisplayName("파일이 존재하지 않을 때 빈 문자열을 반환한다.")
    @Test
    void readFileContent_File_Not_Exists() throws IOException {
        // given
        final var filePath = "not-exists.txt";

        // when
        final var result = FileUtils.readFileContent(filePath);

        // then
        assertEquals("", result);
    }

    @DisplayName("파일을 읽어온다.")
    @Test
    void readFileContent() throws IOException {
        // given
        final var fileName = "nextstep.txt";
        final String filePath = ClassLoader.getSystemResource(fileName).getPath();

        // when
        final var result = FileUtils.readFileContent(filePath);

        // then
        assertEquals("nextstep", result);
    }
}
