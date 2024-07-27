package org.apache.coyote.http11.parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpRequestHeaderDto {

    public static final String CONTENT_LENGTH = "Content-Length";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final Pattern DELIMITER = Pattern.compile(":");
    public final Map<String, String> requestHeader;

    public HttpRequestHeaderDto(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public static HttpRequestHeaderDto of(List<String> requestHeader) {
        Map<String, String> requestHeaders = requestHeader
            .stream()
            .map(line -> DELIMITER.split(line))
            .collect(Collectors.toMap(words -> words[KEY_INDEX].trim(), words -> words[VALUE_INDEX].trim()));

        return new HttpRequestHeaderDto(requestHeaders);
    }

    public int getContentLength() {
        if (Objects.isNull(requestHeader.get(CONTENT_LENGTH))) {
            return 0;
        }

        return Integer.parseInt(requestHeader.get(CONTENT_LENGTH));
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }
}
