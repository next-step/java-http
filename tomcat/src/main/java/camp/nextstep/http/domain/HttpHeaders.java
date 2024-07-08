package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final long MIN_CONTENT_LENGTH = 1L;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final int EMPTY_CONTENT_LENGTH = 0;
    private final Map<String, String> headers;

    public HttpHeaders() {
        headers = new HashMap<>();
    }

    public void setContentType(final ContentType contentType) {
        headers.put(CONTENT_TYPE, contentType.getType());
    }

    public ContentType getContentType() {
        return ContentType.from(headers.get(CONTENT_TYPE));
    }

    public void setContentLength(final long contentLength) {
        if (contentLength < MIN_CONTENT_LENGTH) {
            throw new InvalidHttpHeaderException("ContentLength must be grater than 0");
        }

        headers.put(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    public long getContentLength() {
        final String value = headers.get(CONTENT_LENGTH);
        if (value == null) {
            return EMPTY_CONTENT_LENGTH;
        }
        return Long.parseLong(value);
    }
}
