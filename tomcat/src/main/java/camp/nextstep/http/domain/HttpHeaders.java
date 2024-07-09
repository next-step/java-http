package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeaders {

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final String HEADER_DELIMITER = ":";
    private static final int SPLIT_LIMIT = 2;
    private static final String CONTENT_TYPE = "Content-Type";
    private static final long MIN_CONTENT_LENGTH = 1L;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final int EMPTY_CONTENT_LENGTH = 0;
    public static final String LOCATION = "Location";

    private final Map<String, String> headers;

    public HttpHeaders() {
        headers = new LinkedHashMap<>();
    }

    public HttpHeaders(final List<String> headers) {
        this.headers = headers.stream()
                .map(this::splitHeader)
                .collect(Collectors.toMap(
                        header -> header[KEY_INDEX].trim(),
                        header -> header[VALUE_INDEX].trim(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private String[] splitHeader(final String headerLine) {
        if (headerLine.contains(HEADER_DELIMITER)) {
            return headerLine.split(HEADER_DELIMITER, SPLIT_LIMIT);
        }
        throw new InvalidHttpHeaderException("Invalid HTTP header line: " + headerLine);
    }

    public void setContentType(final ContentType contentType) {
        headers.put(CONTENT_TYPE, contentType.getType());
    }

    public ContentType getContentType() {
        return ContentType.from(headers.get(CONTENT_TYPE));
    }

    public void setContentLength(final int contentLength) {
        if (contentLength < MIN_CONTENT_LENGTH) {
            throw new InvalidHttpHeaderException("ContentLength must be grater than 0");
        }

        headers.put(CONTENT_LENGTH, String.valueOf(contentLength));
    }

    public int getContentLength() {
        final String value = headers.getOrDefault(CONTENT_LENGTH, String.valueOf(EMPTY_CONTENT_LENGTH));
        return Integer.parseInt(value);
    }

    public boolean isContentLengthEmpty() {
        return getContentLength() == EMPTY_CONTENT_LENGTH;
    }

    public void setLocation(final String location) {
        headers.put(LOCATION, location);
    }

    public String convertToString() {
        return headers.entrySet()
                .stream()
                .map(entry -> String.format("%s%s %s ", entry.getKey(), HEADER_DELIMITER, entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public void add(final String key, final String value) {
        headers.put(key, value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpHeaders that = (HttpHeaders) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(headers);
    }
}
