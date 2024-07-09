package nextstep.org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import support.StubFile;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ContentType의 특징
 * - file 확장자를 보고, 응답 해주는 콘텐츠의 타입을 알려주는 객체이다.
 * - 일단은 당장 필요한 아래 두 개의 값만 생각하자
 *   - text/css
 *   - text/html
 */
public class ContentTypeTest {
    enum ContentType {
        HTML(".html", "text/html"),
        CSS(".css", "text/css"),
        TEXT("", "text/plain")
        ;

        private final String fileExtension;
        private final String value;

        ContentType(final String fileExtension, final String value) {
            this.fileExtension = fileExtension;
            this.value = value;
        }

        public static ContentType from(final File file) {
            String fileName = file.getName();
            if (fileName.endsWith(HTML.fileExtension)) {
                return HTML;
            }

            if (fileName.endsWith(CSS.fileExtension)) {
                return CSS;
            }

            return TEXT;
        }

        public String getValue() {
            return value;
        }
    }

    @DisplayName("File을 받아서 확장자를 확인하고 적절한 ContentType을 생성한다.")
    @ParameterizedTest(name = "file = {0}")
    @CsvSource({
            "ttt.html, text/html",
            "ttt.css, text/css",
            "ttt, text/plain",
    })
    void from(String fileName, String contentTypeValue) {
        StubFile htmlFile = new StubFile(fileName);

        ContentType contentType = ContentType.from(htmlFile);

        assertThat(contentType.getValue()).isEqualTo(contentTypeValue);
    }
}
