package camp.nextstep.request;

import java.util.Map;

public class RequestHeaders {
    // XXX: 변수명
    private final Map<String, String> map;

    public RequestHeaders(Map<String, String> map) {
        this.map = map;
    }

    public Integer getContentLength() {
        String contentLength = map.get("Content-Length");
        if (contentLength == null) return null;

        return Integer.parseInt(contentLength);
    }

    public String getCookieHeader() {
        return map.get("Cookie");
    }
}
