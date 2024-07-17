package org.apache.coyote.http11.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InputStream에 대한 Parsing을 진행합니다.
 *
 * @return HttpRequestFormat
 */
public class HttpRequestParser {

    public static final String SPACE = " ";
    private static final Logger log = LoggerFactory.getLogger(HttpRequestParser.class);

    public static HttpRequestDto of(final InputStream inputStream) throws IOException {
        String requestLine = parseMethodAndUrl(inputStream);
        log.info(requestLine);
        return HttpRequestDto.of(List.of(requestLine.split(SPACE)));
    }

    private static String parseMethodAndUrl(final InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.readLine().trim();
    }
}
