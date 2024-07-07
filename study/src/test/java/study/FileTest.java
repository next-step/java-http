package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 웹서버는 사용자가 요청한 html 파일을 제공 할 수 있어야 한다.
 * File 클래스를 사용해서 파일을 읽어오고, 사용자에게 전달한다.
 */
@DisplayName("File 클래스 학습 테스트")
class FileTest {

    /**
     * resource 디렉터리 경로 찾기
     *
     * File 객체를 생성하려면 파일의 경로를 알아야 한다.
     * 자바 애플리케이션은 resource 디렉터리에 HTML, CSS 같은 정적 파일을 저장한다.
     * resource 디렉터리의 경로는 어떻게 알아낼 수 있을까?
     */
    @Test
    void resource_디렉터리에_있는_파일의_경로를_찾는다() {
        final String fileName = "nextstep.txt";

        final String workingDirPath = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString();

        final String resourcePath = "/src/test/resources";

        final String actual = Paths.get(workingDirPath, resourcePath, fileName)
                .toString();

        assertThat(actual).endsWith(fileName);
    }

    @DisplayName("ClassLoader를 이용해서 클래스 패스에서 파일 이름으로 파일 찾기")
    @Test
    void 파일_경로_찾기() {
        final String fileName = "nextstep.txt";

        // build/resources, build/classes 에서 파일을 찾는다
        URL resource = getClass()
                .getClassLoader()
                .getResource(fileName);  // nextstep.txt 파일은 test/resources 에 위치하므로 build/resources/test 에서 찾게 된다.

        assertThat(resource.toString()).endsWith(fileName);
    }

    /**
     * 파일 내용 읽기
     *
     * 읽어온 파일의 내용을 I/O Stream을 사용해서 사용자에게 전달 해야 한다.
     * File, Files 클래스를 사용하여 파일의 내용을 읽어보자.
     */
    @Test
    void 파일의_내용을_읽는다() throws IOException {
        final String fileName = "nextstep.txt";

        final String workingDirPath = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString();

        final String resourcePath = "/src/test/resources";

        final Path filePath = Paths.get(workingDirPath, resourcePath, fileName);

        final List<String> actual = Files.readAllLines(filePath);

        assertThat(actual).containsOnly("nextstep");
    }
}
