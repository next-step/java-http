package camp.nextstep.domain.http;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class HttpHeaders {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String COOKIE_HEADER_KEY = "Cookie";

    private static final String REQUEST_HEADER_FORMAT_SPLIT_REGEX = ": ";

    private static final int REQUEST_HEADER_FORMAT_LENGTH = 2;
    private static final int REQUEST_HEADER_KEY_INDEX = 0;
    private static final int REQUEST_HEADER_VALUE_INDEX = 1;

    private final Map<String, String> headers;

    public HttpHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpHeaders() {
        this(new HashMap<>());
    }

    public static HttpHeaders from(List<String> headers) {
        return new HttpHeaders(parseHeaders(headers));
    }

    private static Map<String, String> parseHeaders(List<String> headers) {
        return headers.stream()
                .filter(header -> !header.isEmpty())
                .map(HttpHeaders::parseHeader)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private static Map.Entry<String, String> parseHeader(String header) {
        String[] splitRequestHeader = header.split(REQUEST_HEADER_FORMAT_SPLIT_REGEX);
        if (splitRequestHeader.length != REQUEST_HEADER_FORMAT_LENGTH) {
            throw new IllegalArgumentException("Header값이 정상적으로 입력되지 않았습니다 - " + header);
        }
        return new AbstractMap.SimpleEntry<>(
                splitRequestHeader[REQUEST_HEADER_KEY_INDEX],
                splitRequestHeader[REQUEST_HEADER_VALUE_INDEX]
        );
    }

    public boolean containsContentLength() {
        return headers.containsKey(CONTENT_LENGTH_HEADER_KEY);
    }

    public int getContentLength() {
        if (!containsContentLength()) {
            throw new IllegalStateException("Content-Length가 존재하지 않습니다.");
        }
        return parseInt(headers.get(CONTENT_LENGTH_HEADER_KEY));
    }

    public boolean containsCookie() {
        return headers.containsKey(COOKIE_HEADER_KEY);
    }

    public String getCookie() {
        if (!containsCookie()) {
            throw new IllegalStateException("Cookie가 존재하지 않습니다.");
        }
        return headers.get(COOKIE_HEADER_KEY);
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
