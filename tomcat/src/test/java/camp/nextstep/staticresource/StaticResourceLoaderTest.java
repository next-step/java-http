package camp.nextstep.staticresource;

import camp.nextstep.exception.RequestNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaticResourceLoaderTest {
    private final StaticResourceLoader staticResourceLoader = new StaticResourceLoader();

    @Test
    void readStaticFile() throws IOException {
        String fileContent = staticResourceLoader.readAllLines("static/index.html");
        assertThat(fileContent).startsWith("<!DOCTYPE html>");
        assertThat(fileContent).hasLineCount(106);
    }

    @Test
    void readMissingFile() {
        assertThrows(RequestNotFoundException.class,
                () -> staticResourceLoader.readAllLines("static/invalid-filename.html"));
    }
}
