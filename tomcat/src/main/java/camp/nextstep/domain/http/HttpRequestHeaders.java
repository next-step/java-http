package camp.nextstep.domain.http;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class HttpRequestHeaders {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";

    private static final String REQUEST_HEADER_FORMAT_SPLIT_REGEX = ": ";

    private static final int REQUEST_HEADER_FORMAT_LENGTH = 2;
    private static final int REQUEST_HEADER_KEY_INDEX = 0;
    private static final int REQUEST_HEADER_VALUE_INDEX = 1;

    private final Map<String, String> headers;

    public HttpRequestHeaders(List<String> headers) {
        this.headers = headers.stream()
                .map(this::parseRequestHeader)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, String> parseRequestHeader(String requestHeader) {
        String[] splitRequestHeader = requestHeader.split(REQUEST_HEADER_FORMAT_SPLIT_REGEX);
        if (splitRequestHeader.length != REQUEST_HEADER_FORMAT_LENGTH) {
            throw new IllegalArgumentException("RequestHeader값이 정상적으로 입력되지 않았습니다 - " + requestHeader);
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

    public Map<String, String> getHeaders() {
        return headers;
    }
}
