package camp.nextstep.domain.http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String LOCATION_HEADER_KEY = "Location";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String EMPTY_RESPONSE_BODY = "";

    private static final String RESPONSE_DELIMITER = "\r\n";
    private static final String RESPONSE_STATUS_LINE_FORMAT = "%s %s %s ";
    private static final String RESPONSE_HEADER_FORMAT = "%s: %s ";

    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;
    private final Map<String, String> httpHeaders;
    private final String responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatus httpStatus, Map<String, String> httpHeaders, String responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
        this.httpHeaders = parseHttpHeaders(httpHeaders, responseBody);
        this.responseBody = responseBody;
    }

    private Map<String, String> parseHttpHeaders(Map<String, String> httpHeaders, String responseBody) {
        if (responseBody.isEmpty()) {
            return httpHeaders;
        }
        Map<String, String> headers = new LinkedHashMap<>(httpHeaders);
        headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(responseBody.getBytes().length));
        return headers;
    }

    public static HttpResponse ok(HttpProtocol httpProtocol, Map<String, String> httpHeaders, String responseBody) {
        return new HttpResponse(httpProtocol, HttpStatus.OK, httpHeaders, responseBody);
    }

    public static HttpResponse ok(HttpProtocol httpProtocol, ContentType contentType, String responseBody) {
        return new HttpResponse(httpProtocol, HttpStatus.OK, Map.of(CONTENT_TYPE_HEADER_KEY, contentType.getUtf8ContentType()), responseBody);
    }

    public static HttpResponse found(HttpProtocol httpProtocol, String location) {
        return new HttpResponse(httpProtocol, HttpStatus.FOUND, Map.of(LOCATION_HEADER_KEY, location), EMPTY_RESPONSE_BODY);
    }

    public String buildResponse() {
        String response = buildBaseResponse();
        if (responseBody.isEmpty()) {
            return response;
        }
        return String.join(RESPONSE_DELIMITER, response, "", responseBody);
    }

    private String buildBaseResponse() {
        if (httpHeaders.isEmpty()) {
            return buildStatusLine();
        }
        return String.join(RESPONSE_DELIMITER, buildStatusLine(), buildHeaders());
    }

    private String buildStatusLine() {
        return String.format(RESPONSE_STATUS_LINE_FORMAT, httpProtocol.buildProtocol(), httpStatus.getStatusCode(), httpStatus.getReasonPhrase());
    }

    private String buildHeaders() {
        return httpHeaders.entrySet()
                .stream()
                .map(header -> String.format(RESPONSE_HEADER_FORMAT, header.getKey(), header.getValue()))
                .collect(Collectors.joining(RESPONSE_DELIMITER));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }
}
