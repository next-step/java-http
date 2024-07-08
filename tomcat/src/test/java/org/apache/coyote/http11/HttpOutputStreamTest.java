package org.apache.coyote.http11;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpOutputStreamTest {

    @Test
    void HttpOutputStream_에_내용을_쓸_수_있다() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final HttpOutputStream httpOutputStream = new HttpOutputStream(outputStream);

        httpOutputStream.write(new TestHttpWritable("test"));

        assertThat(outputStream.toString()).isEqualTo("test");
    }

    @Test
    void HttpOutputStream_에_줄바꿈을_추가할_수_있다() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final HttpOutputStream httpOutputStream = new HttpOutputStream(outputStream);

        httpOutputStream.writeLineSeparator();

        assertThat(outputStream.toString()).isEqualTo(System.lineSeparator());
    }

    private record TestHttpWritable(String content) implements HttpWritable {
        @Override
        public byte[] getBytes() {
            return content.getBytes();
        }
    }
}
