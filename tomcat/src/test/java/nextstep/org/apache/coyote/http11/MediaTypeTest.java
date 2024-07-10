package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import support.StubFile;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MediaTypeTest {
    @DisplayName("File을 받아서 확장자를 확인하고 적절한 ContentType을 생성한다.")
    @ParameterizedTest(name = "file = {0}")
    @CsvSource({
            "ttt.html, text/html",
            "ttt.css, text/css",
            "ttt, text/plain",
    })
    void from(String fileName, String contentTypeValue) {
        StubFile htmlFile = new StubFile(fileName);

        MediaType mediaType = MediaType.from(htmlFile);

        assertThat(mediaType.getValue()).isEqualTo(contentTypeValue);
    }
}
