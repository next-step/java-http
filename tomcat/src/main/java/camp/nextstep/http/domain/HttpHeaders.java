package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final long MIN_CONTENT_LENGTH = 1L;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String EMPTY_CONTENT_LENGTH = "0";

    private final Map<String, String> headers;

    public HttpHeaders() {
        headers = new LinkedHashMap<>();
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
        final String value = headers.getOrDefault(CONTENT_LENGTH, EMPTY_CONTENT_LENGTH);
        return Long.parseLong(value);
    }

    public String convertToString() {
        return headers.entrySet()
                .stream()
                .map(entry -> String.format("%s: %s ", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void add(final String key, final String value) {
        headers.put(key, value);
    }
}
