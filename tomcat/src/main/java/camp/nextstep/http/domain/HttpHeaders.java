package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpHeaders {

    public static final String DELIMITER = ":";
    public static final String JSESSIONID = "JSESSIONID";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final long MIN_CONTENT_LENGTH = 1L;
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final int EMPTY_CONTENT_LENGTH = 0;
    private static final String LOCATION = "Location";
    private static final String COOKIE_KEY = "Cookie";

    private final Map<String, String> headers;
    private final HttpCookies httpCookies;

    public HttpHeaders() {
        this(new LinkedHashMap<>());
    }

    public HttpHeaders(final Map<String, String> headers) {
        this.headers = headers;
        this.httpCookies = new HttpCookies(headers.get(COOKIE_KEY));
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

    public void add(final String key, final String value) {
        headers.put(key, value);
    }

    public String getCookie(final String name) {
        return httpCookies.get(name);
    }

    public String convertToString() {
        return convertCookiesToString() +
                headers.entrySet()
                        .stream()
                        .map(entry -> String.format("%s%s %s ", entry.getKey(), DELIMITER, entry.getValue()))
                        .collect(Collectors.joining(System.lineSeparator()));
    }

    private String convertCookiesToString() {
        if (httpCookies.isEmpty()) {
            return "";
        }
        return httpCookies.convertToString() + System.lineSeparator();
    }

    public void addSessionCookie(final String value) {
        httpCookies.add(JSESSIONID, value);
    }

    public String getSessionCookie() {
        return httpCookies.get(JSESSIONID);
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
