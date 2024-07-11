package camp.nextstep.domain.http.response;

import camp.nextstep.domain.http.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpResponse {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String LOCATION_HEADER_KEY = "Location";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String EMPTY_RESPONSE_BODY = "";

    private static final String COOKIE_RESPONSE_HEADER_KEY = "Set-Cookie";
    private static final String HEADER_FORMAT = "%s: %s ";

    private static final String RESPONSE_DELIMITER = "\r\n";
    private static final String RESPONSE_STATUS_LINE_FORMAT = "%s %s %s ";

    private final HttpProtocol httpProtocol;
    private final HttpStatus httpStatus;
    private final HttpHeaders httpHeaders;
    private final HttpCookie httpCookie;
    private final String responseBody;

    public HttpResponse(HttpProtocol httpProtocol, HttpStatus httpStatus, Map<String, String> httpHeaders, HttpCookie httpCookie, String responseBody) {
        this.httpProtocol = httpProtocol;
        this.httpStatus = httpStatus;
        this.httpHeaders = parseHttpHeaders(httpHeaders, responseBody);
        this.httpCookie = httpCookie;
        this.responseBody = responseBody;
    }

    private HttpHeaders parseHttpHeaders(Map<String, String> httpHeaders, String responseBody) {
        if (responseBody.isEmpty()) {
            return new HttpHeaders(httpHeaders);
        }
        Map<String, String> headers = new LinkedHashMap<>(httpHeaders);
        headers.put(CONTENT_LENGTH_HEADER_KEY, String.valueOf(responseBody.getBytes().length));
        return new HttpHeaders(headers);
    }

    public static HttpResponse ok(HttpProtocol httpProtocol, ContentType contentType, String responseBody) {
        return new HttpResponse(
                httpProtocol,
                HttpStatus.OK,
                Map.of(CONTENT_TYPE_HEADER_KEY, contentType.getUtf8ContentType()),
                new HttpCookie(),
                responseBody
        );
    }

    public static HttpResponse found(HttpProtocol httpProtocol, String location) {
        return new HttpResponse(
                httpProtocol,
                HttpStatus.FOUND,
                Map.of(LOCATION_HEADER_KEY, location),
                new HttpCookie(),
                EMPTY_RESPONSE_BODY
        );
    }

    public HttpResponse addCookie(HttpCookie cookie) {
        httpCookie.addCookie(cookie);
        return this;
    }

    public String buildResponse() {
        String response = buildBaseResponse();
        if (responseBody.isEmpty()) {
            return response;
        }
        return String.join(RESPONSE_DELIMITER, response, "", responseBody);
    }

    private String buildBaseResponse() {
        if (httpHeaders.isEmpty() && httpCookie.isEmpty()) {
            return buildStatusLine();
        }
        return String.join(RESPONSE_DELIMITER, buildStatusLine(), buildHeaders());
    }

    private String buildStatusLine() {
        return String.format(RESPONSE_STATUS_LINE_FORMAT, httpProtocol.buildProtocol(), httpStatus.getStatusCode(), httpStatus.getReasonPhrase());
    }

    private String buildHeaders() {
        return Stream.of(httpHeaders.getHeaders(), Map.of(COOKIE_RESPONSE_HEADER_KEY, httpCookie.getCookieHeaderFormat()))
                .flatMap(map -> map.entrySet().stream())
                .filter(header -> !header.getValue().isEmpty())
                .map(header -> String.format(HEADER_FORMAT, header.getKey(), header.getValue()))
                .collect(Collectors.joining(RESPONSE_DELIMITER));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpCookie getHttpCookie() {
        return httpCookie;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
