package camp.nextstep.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestHeaders {
    public static HttpRequestHeaders parse(List<String> list) {
        final Map<String, String> headersMap = new HashMap<>();
        list.forEach(line -> {
            int index = line.indexOf(": ");
            String key = line.substring(0, index);
            String value = line.substring(index + 2);
            headersMap.put(key, value);
        });

        return new HttpRequestHeaders(headersMap);
    }

    private final Map<String, String> headersMap;

    private HttpRequestHeaders(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public Integer getContentLength() {
        String contentLength = headersMap.get("Content-Length");
        if (contentLength == null) return null;

        return Integer.parseInt(contentLength);
    }

    public String getCookieHeader() {
        return headersMap.get("Cookie");
    }
}