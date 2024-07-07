package org.apache.coyote.http11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class StreamToStringListConverter {
    private StreamToStringListConverter() {
    }

    public static List<String> parseToStringList(final InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException("Input stream cannot be null");
        }

        final List<String> list = new ArrayList<>();

        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            bufferedReader.lines()
                    .forEach(line -> addList(list, line));
        }

        return list;
    }

    private static void addList(final List<String> list, final String line) {
        if (line == null) {
            return;
        }
        
        list.add(line);
    }
}
