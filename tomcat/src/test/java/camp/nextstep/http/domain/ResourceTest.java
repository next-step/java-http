package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceTest {

    @Test
    void resource_아래의_파일을_byte배열로_변환할_수_있다() throws IOException {
        final Resource resource = new Resource("nextstep.txt");

        final byte[] actual = resource.readAllBytes();

        assertThat(actual).containsOnly("nextstep".getBytes());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "nextstep.txt:true",
            "notExist.none:false"
    }, delimiter = ':')
    void resource_아래의_파일이_존재여부를_반환받을_수_있다(final String path, final boolean expected) throws IOException {
        final Resource resource = new Resource(path);

        assertThat(resource.exists()).isEqualTo(expected);
    }
}
