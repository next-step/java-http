package support;

import camp.nextstep.http.domain.HttpMethod;
import camp.nextstep.http.domain.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MockHttpRequestBuilder {
    private static final String HTTP_REQUEST_TEMPLATE =
            "%s %s HTTP/1.1 \r\n" +
                    "%s" +
                    "\r\n" +
                    "%s";
    private static final String DEFAULT_URI = "/";
    private static final Map<String, String> DEFAULT_HEADERS = Map.of("Host", "localhost:8080", "Connection", "keep-alive");
    public static final String HEADER_DELIMITER = ": ";

    private HttpMethod method;
    private String requestURI;
    private Map<String, String> headers;
    private String body;

    public MockHttpRequestBuilder() {
        this.method = HttpMethod.GET;
        this.requestURI = DEFAULT_URI;
        this.headers = new HashMap<>(DEFAULT_HEADERS);
        this.body = "";
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

    public MockHttpRequestBuilder addHeaders(final Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public MockHttpRequestBuilder body(final String body) {
        this.body = body;
        return this;
    }

    public HttpRequest build() throws Exception {
        final String requestString = generateRequestString();
        final InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());
        return new HttpRequest(inputStream);
    }

    private String generateRequestString() {
        final String headersString = headers.entrySet().stream()
                .map(entry -> entry.getKey() + HEADER_DELIMITER + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));

        return String.format(HTTP_REQUEST_TEMPLATE, method, requestURI, headersString, body);
    }

}
