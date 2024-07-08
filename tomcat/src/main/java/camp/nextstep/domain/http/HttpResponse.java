package camp.nextstep.domain.http;

import java.util.Map;

import static java.util.Collections.emptyMap;

public class HttpResponse {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";

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

    public static HttpResponse found(HttpProtocol httpProtocol, String responseBody) {
        return new HttpResponse(httpProtocol, HttpStatus.FOUND, responseBody);
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
