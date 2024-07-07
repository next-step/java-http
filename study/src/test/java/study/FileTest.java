package study;

import com.google.common.reflect.Reflection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ClassPathUtils;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.util.ResourceUtils;

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
    void resource_디렉터리에_있는_파일의_경로를_찾는다() throws FileNotFoundException {
        final String fileName = "nextstep.txt";

        // todo
        String currentDirectory = new File("").getAbsolutePath();
        System.out.println("currentDirectory = " + currentDirectory);
        // /Users/jaypark/private/java-http/study
        // /Users/jaypark/private/java-http/study/src/test/resources

        var file = new File(currentDirectory);
        for (File listFile : file.listFiles()) {
            if (listFile.getPath().endsWith("src")) {
                for (File listedFile : listFile.listFiles()) {
                    if (listedFile.getPath().endsWith("test")) {
                        for (File file1 : listedFile.listFiles()) {
                            if (file1.getPath().endsWith("resources")) {
                                var files = file1.listFiles();
                                System.out.println("files = " + Arrays.toString(files));
                                String actual = files[0].getName();
                                assertThat(actual).endsWith(fileName);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 파일 내용 읽기
     *
     * 읽어온 파일의 내용을 I/O Stream을 사용해서 사용자에게 전달 해야 한다.
     * File, Files 클래스를 사용하여 파일의 내용을 읽어보자. + Path
     */
    @Test
    void 파일의_내용을_읽는다() throws IOException {
        final String fileContents= "nextstep";
/*
        // todo
        final PathResource path = new PathResource("/Users/jaypark/private/java-http/study/src/test/resources/nextstep.txt");

        var a = path.getInputStream().readAllBytes();

        StringBuilder builder =  new StringBuilder();
        System.out.println("builder = " + new String(a));*/


        Path path2 = Paths.get("/Users/jaypark/private/java-http/study/src/test/resources/nextstep.txt");

        var bufferedReader = Files.newBufferedReader(path2);
        var bufferLine = bufferedReader.lines();


        // todo
        final List<String> actual =  bufferLine.collect(Collectors.toList());
        assertThat(actual).containsOnly("nextstep");
    }


}
