package camp.nextstep.domain.http;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

public class HttpResponse {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String EMPTY_RESPONSE_BODY = "";

    private static final String RESPONSE_DELIMITER = "\r\n";
    private static final String RESPONSE_STATUS_LINE_FORMAT = "%s %s %s ";
    private static final String RESPONSE_HEADER_FORMAT = "%s: %s ";

    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;
    private final Map<String, String> httpHeaders;
    private final String responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatus httpStatus, String responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
        this.httpHeaders = extractHttpHeaders(responseBody);
        this.responseBody = responseBody;
    }

    private Map<String, String> extractHttpHeaders(String responseBody) {
        if (!responseBody.isEmpty()) {
            return Map.of(CONTENT_LENGTH_HEADER_KEY, String.valueOf(responseBody.getBytes().length));
        }
        return emptyMap();
    }

    public static HttpResponse ok(HttpProtocol httpProtocol, String responseBody) {
        return new HttpResponse(httpProtocol, HttpStatus.OK, responseBody);
    }

    public static HttpResponse found(HttpProtocol httpProtocol) {
        return new HttpResponse(httpProtocol, HttpStatus.FOUND, EMPTY_RESPONSE_BODY);
    }

    public String buildResponse() {
        String response = String.join(RESPONSE_DELIMITER, buildStatusLine(), buildHeaders());
        return String.join(RESPONSE_DELIMITER, response, "", responseBody);
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

    public HttpProtocol getHttpProtocol() {
        return httpProtocol;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
