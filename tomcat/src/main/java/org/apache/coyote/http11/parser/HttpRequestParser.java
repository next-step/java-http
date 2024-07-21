package org.apache.coyote.http11.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InputStream에 대한 Parsing을 진행합니다.
 *
 * @return HttpRequestFormat
 */
public class HttpRequestParser {

    private static final Pattern DELIMITER = Pattern.compile(" ");
    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);

    public static HttpRequestDto parse(final InputStream inputStream) throws IOException {
        String requestLine = parseMethodAndUrl(inputStream);
        log.info(requestLine);
        return HttpRequestDto.of(List.of(DELIMITER.split(requestLine)));
    }

    private static String parseMethodAndUrl(final InputStream inputStream) throws IOException {

        try (BufferedReader bufferedReader =
            new BufferedReader(new InputStreamReader(inputStream))
        ) {
            return bufferedReader.readLine().trim();
        }

    }
}
