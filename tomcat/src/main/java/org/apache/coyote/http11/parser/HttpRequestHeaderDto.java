package org.apache.coyote.http11.parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.coyote.http11.MapUtil;

public class HttpRequestHeaderDto {

    public static final String CONTENT_LENGTH = "Content-Length";
    private static final Pattern DELIMITER = Pattern.compile(":");

    public final Map<String, String> requestHeader;

    public HttpRequestHeaderDto(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public static HttpRequestHeaderDto of(List<String> requestHeader) {
        Map<String, String> requestHeaders = MapUtil.parseToMap(requestHeader.toArray(new String[0]), DELIMITER);

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
