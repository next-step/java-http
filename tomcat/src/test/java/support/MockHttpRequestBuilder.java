package support;

import camp.nextstep.http.domain.HttpHeaders;
import camp.nextstep.http.domain.HttpMethod;
import camp.nextstep.http.domain.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MockHttpRequestBuilder {
    private static final String HTTP_REQUEST_TEMPLATE =
            "%s %s HTTP/1.1 " +
                    System.lineSeparator() +
                    "%s" +
                    System.lineSeparator() +
                    System.lineSeparator() +
                    "%s";
    private static final String DEFAULT_URI = "/";
    private static final Map<String, String> DEFAULT_HEADERS = Map.of("Host", "localhost:8080", "Connection", "keep-alive");
    private static final String HEADER_DELIMITER = ": ";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String BODY_DELIMITER = "&";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String COOKIE_KEY = "Cookie";

    private HttpMethod method;
    private String requestURI;
    private final Map<String, String> headers;
    private final Map<String, String> body;

    public MockHttpRequestBuilder() {
        this.method = HttpMethod.GET;
        this.requestURI = DEFAULT_URI;
        this.headers = new HashMap<>(DEFAULT_HEADERS);
        this.body = new HashMap<>();
    }

    public MockHttpRequestBuilder method(final HttpMethod method) {
        this.method = method;
        return this;
    }

    public MockHttpRequestBuilder requestURI(final String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public MockHttpRequestBuilder addHeader(final String key, final String value) {
        this.headers.put(key, value);
        return this;
    }

    public MockHttpRequestBuilder addCookie(final String key, final String value) {
        this.headers.put(COOKIE_KEY, String.join(KEY_VALUE_DELIMITER, key, value));
        return this;
    }

    public MockHttpRequestBuilder addSessionCookie(final String value) {
        return addCookie(HttpHeaders.JSESSIONID, value);
    }

    public MockHttpRequestBuilder addBody(final String key, final String value) {
        this.body.put(key, value);
        return this;
    }

    public HttpRequest build() throws Exception {
        final String requestString = generateRequestString();
        final InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());
        return new HttpRequest(inputStream);
    }

    private String generateRequestString() {
        final String bodyString = generateBodyString();

        if (!bodyString.isEmpty()) {
            headers.put(CONTENT_LENGTH, String.valueOf(bodyString.length()));
        }

        final String headersString = headers.entrySet().stream()
                .map(entry -> entry.getKey() + HEADER_DELIMITER + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));

        return String.format(HTTP_REQUEST_TEMPLATE, method, requestURI, headersString, bodyString);
    }

    private String generateBodyString() {
        return body.entrySet().stream()
                .map(entry -> entry.getKey() + KEY_VALUE_DELIMITER + entry.getValue())
                .collect(Collectors.joining(BODY_DELIMITER));
    }

}
