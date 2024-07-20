package org.apache.coyote.http11;

import java.io.BufferedReader;
import java.io.IOException;

public final class HttpMessageConverter {
    
    private HttpMessageConverter() {
    }

    public static String convert(BufferedReader bufferedReader, int length) throws IOException {
        final var buffer = new char[length];
        int readCount = bufferedReader.read(buffer, 0, length);
        if (length != readCount) {
            throw new IOException("Length is not matched: " + length + " != " + readCount);
        }
        return new String(buffer);
    }
}
