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

    public static HttpRequestDto parse(final BufferedReader bufferedReader) throws IOException {
        String requestLine = parseMethodAndUrl(bufferedReader);
        log.info(requestLine);
        return HttpRequestDto.of(List.of(DELIMITER.split(requestLine)));
    }

    private static String parseMethodAndUrl(final BufferedReader bufferedReader) throws IOException {

        return bufferedReader.readLine().trim();
    }
}
