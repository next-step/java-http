package org.apache.coyote.http11.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.coyote.http11.MapUtil;
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
    private static final Pattern BODY_DELIMITER = Pattern.compile("=");
    private static final String AMPERSEND = "&";

    public static HttpRequestDto parse(final BufferedReader bufferedReader) throws IOException {
        String requestLine = parseMethodAndUrl(bufferedReader);
        log.info(requestLine);

        List<String> headers = parseHeader(bufferedReader);
        log.info(String.valueOf(headers));

        HttpRequestHeaderDto requestHeaders = HttpRequestHeaderDto.of(headers);

        String body = parseBody(bufferedReader, requestHeaders.getContentLength());
        Map<String,String> requestBody = MapUtil.parseToMap(body.split("&"), BODY_DELIMITER);
        return HttpRequestDto.of(List.of(DELIMITER.split(requestLine)), requestHeaders, requestBody);
    }

    private static String parseMethodAndUrl(final BufferedReader bufferedReader)
            throws IOException {
        return bufferedReader.readLine().trim();
    }

    private static List<String> parseHeader(final BufferedReader bufferedReader)
            throws IOException {

        String header = bufferedReader.readLine();
        List<String> headers = new ArrayList<>();

        while (Objects.nonNull(header) && !header.isEmpty()) {
            headers.add(header.trim());
            header = bufferedReader.readLine();
        }

        return headers;
    }

    private static String parseBody(final BufferedReader bufferedReader, int contentLength)
            throws IOException {

        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);

        return new String(buffer);
    }

}
