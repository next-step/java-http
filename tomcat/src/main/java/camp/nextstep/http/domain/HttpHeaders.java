package camp.nextstep.http.domain;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private final Map<String, String> headers;

    public HttpHeaders() {
        headers = new HashMap<>();
    }

    public void setContentType(final String contentType) {
        headers.put("Content-Type", contentType);
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }

    public void setContentLength(final long contentLength) {
        headers.put("Content-Length", String.valueOf(contentLength));
    }

    public long getContentLength() {
        final String value = headers.get("Content-Length");
        if (value == null) {
            return 0;
        }
        return Long.parseLong(value);
    }
}
