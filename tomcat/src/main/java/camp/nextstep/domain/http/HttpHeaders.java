package camp.nextstep.domain.http;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class HttpHeaders {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String COOKIE_HEADER_KEY = "Cookie";

    private static final String REQUEST_HEADER_FORMAT_SPLIT_REGEX = ": ";
    private static final String HEADER_FORMAT = "%s: %s ";
    private static final String HEADERS_DELIMITER = "\r\n";

    private static final int REQUEST_HEADER_FORMAT_LENGTH = 2;
    private static final int REQUEST_HEADER_KEY_INDEX = 0;
    private static final int REQUEST_HEADER_VALUE_INDEX = 1;

    private final Map<String, String> generalHeaders;
    private final HttpCookie httpCookie;

    public HttpHeaders(Map<String, String> headers) {
        this.generalHeaders = parseGeneralHeaders(headers);
        this.httpCookie = parseCookie(headers);
    }

    public HttpHeaders(List<String> headers) {
        Map<String, String> parseHeaders = parseHeaders(headers);
        this.generalHeaders = parseGeneralHeaders(parseHeaders);
        this.httpCookie = parseCookie(parseHeaders);
    }

    private static Map<String, String> parseGeneralHeaders(Map<String, String> parseHeaders) {
        return parseHeaders.entrySet()
                .stream()
                .filter(entry -> !COOKIE_HEADER_KEY.equals(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private Map<String, String> parseHeaders(List<String> headers) {
        return headers.stream()
                .filter(header -> !header.isEmpty())
                .map(this::parseHeader)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private Map.Entry<String, String> parseHeader(String header) {
        String[] splitRequestHeader = header.split(REQUEST_HEADER_FORMAT_SPLIT_REGEX);
        if (splitRequestHeader.length != REQUEST_HEADER_FORMAT_LENGTH) {
            throw new IllegalArgumentException("Header값이 정상적으로 입력되지 않았습니다 - " + header);
        }
        return new AbstractMap.SimpleEntry<>(
                splitRequestHeader[REQUEST_HEADER_KEY_INDEX],
                splitRequestHeader[REQUEST_HEADER_VALUE_INDEX]
        );
    }

    private HttpCookie parseCookie(Map<String, String> headers) {
        if (headers.containsKey(COOKIE_HEADER_KEY)) {
            return new HttpCookie(headers.get(COOKIE_HEADER_KEY));
        }
        return new HttpCookie();
    }

    public boolean containsContentLength() {
        return generalHeaders.containsKey(CONTENT_LENGTH_HEADER_KEY);
    }

    public int getContentLength() {
        if (!containsContentLength()) {
            throw new IllegalStateException("Content-Length가 존재하지 않습니다.");
        }
        return parseInt(generalHeaders.get(CONTENT_LENGTH_HEADER_KEY));
    }

    public boolean isEmpty() {
        return generalHeaders.isEmpty() && httpCookie.isEmpty();
    }

    public String buildHeaders() {
        return generateHeaders()
                .map(header -> String.format(HEADER_FORMAT, header.getKey(), header.getValue()))
                .collect(Collectors.joining(HEADERS_DELIMITER));
    }

    public Map<String, String> getHeaders() {
        return generateHeaders().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Stream<Map.Entry<String, String>> generateHeaders() {
        return Stream.of(generalHeaders, Map.of(COOKIE_HEADER_KEY, httpCookie.getCookieHeaderFormat()))
                .flatMap(map -> map.entrySet().stream())
                .filter(header -> !header.getValue().isEmpty());
    }
}
