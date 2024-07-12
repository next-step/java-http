package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceFinderTest {

    private ResourceFinder sut = new ResourceFinder();

    @Nested
    @DisplayName("httpPath 로 파일 내용 조회")
    class FindContext {

        @Test
        @DisplayName("httpPath 와 일치하는 static resource 의 파일 내용을 반환한다")
        void success_getDefaultContext() throws IOException {
            final String httpPath = "/index.html";

            final String actual = sut.findContent(httpPath);

            final URL resource = getClass().getClassLoader().getResource("static/index.html");
            var expected = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("존재하지 않는 경우 에러를 반환한다")
        void fail_notFoundResource() {
            final String httpPath = "/heedoitdox.html";

            assertThrows(ResourceNotFoundException.class,
                    () -> sut.findContent(httpPath));
        }
    }

    @Nested
    @DisplayName("httpPath 로 파일 path 조회")
    class FindFilePath {

        @Test
        @DisplayName("파일이 존재한다")
        void success() {
            String httpPath = "/index.html";

            final Path actual = sut.findFilePath(httpPath);

            final URL resource = getClass().getClassLoader().getResource("static/index.html");
            var expected = new File(resource.getFile());

            assertThat(actual.toString()).isEqualTo(expected.toString());
        }

        @Test
        @DisplayName("파일이 존재하지 않는다")
        void fail() {
            String httpPath = "/heedoitdox.html";

            assertThrows(ResourceNotFoundException.class,
                    () -> sut.findFilePath(httpPath));
        }

        @Test
        @DisplayName("로그인 페이지에 해당하는 파일 path 를 조회한다")
        void success_login() {
            String httpPath = "/login";

            final Path actual = sut.findFilePath(httpPath);

            final URL resource = getClass().getClassLoader().getResource("static/login.html");
            var expected = new File(resource.getFile());

            assertThat(actual.toString()).isEqualTo(expected.toString());
        }
    }

}